package com.mulook.pos.controller;

import com.mulook.pos.Service.DiningTableService;
import com.mulook.pos.Service.TossWidgetService;
import com.mulook.pos.config.TossPaymentConfig;
import com.mulook.pos.dto.DiningTableDto;
import com.mulook.pos.dto.PaymentRequestDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TossWidgetController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DiningTableService diningTableService;
    private final TossWidgetService tossWidgetService;
    private final TossPaymentConfig tossPaymentConfig;


    @GetMapping("/success")
    public String verify(
        @RequestParam String paymentKey,
        @RequestParam String orderId,
        @RequestParam int amount,
        Model model
        ){


        System.out.println("########## verify Controller ##########");
        System.out.println("paymentsKey = " + paymentKey);
        System.out.println("orderId = " + orderId);
        System.out.println("amount = " + amount);
        System.out.println("########## verify Controller ##########");

        tossWidgetService.verify(paymentKey, orderId, amount);

        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);

        return "/tossPay/success.html";
    }
    @GetMapping("/fail")
    public String fail() {
        return "/tossPay/fail.html";
    }

    @PostMapping("/saveAmount")
    public ResponseEntity<String> savePayment(@RequestBody PaymentRequestDto paymentRequestDto){
        System.out.println("###### SaveAmount Controller########");
        System.out.println("paymentRequestDto = " + paymentRequestDto);
        System.out.println("paymentRequestDto.getTossOrderId() = " + paymentRequestDto.getOrderId());
        System.out.println("###### SaveAmount Controller########");
        tossWidgetService.savePayment(paymentRequestDto);
        return ResponseEntity.ok("저장 성공");
    }

    @GetMapping("/tossPay/checkout/{currentName}")
    public String GetTossPay(Model model, @PathVariable int currentName) {

        DiningTableDto currentOrder = diningTableService.findTableOrder(currentName);
        String tossOrderId = generateUniqueOrderId();

        System.out.println("########WidgetController-currentOrder########");
        System.out.println("currentOrder = " + currentOrder);
        System.out.println("tossOrderId = " + tossOrderId);
        System.out.println("########WidgetController-currentOrder########");


        model.addAttribute("currentOrder", currentOrder);
        model.addAttribute("getTotalDiningTablePrice", currentOrder.getTotalDiningTablePrice());
        model.addAttribute("tossOrderId", tossOrderId);

        return "/tossPay/checkout.html";
    }

    @RequestMapping(value = "/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody)
        throws Exception {

        System.out.println("############WidgetController-confirmPayment##########");
        System.out.println("############WidgetController-confirmPayment##########");

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ;
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(
            (widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream =
            isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        System.out.println("########## tossWidgetController confirm #################");
        System.out.println("jsonObject = " + jsonObject);
        System.out.println("########## tossWidgetController confirm #################");
        tossWidgetService.successPayment(jsonObject);

        return ResponseEntity.status(code).body(jsonObject);
    }

    // tossOrderId로 사용할 랜덤 UUID 생성기
    private String generateUniqueOrderId() {
        String currentTime = String.valueOf(System.currentTimeMillis()); // 현재 시간을 밀리초로 가져옴
        String randomString = UUID.randomUUID().toString().substring(0, 6); // 랜덤 문자열 일부만 사용 (예: 앞 6자리)
        return "ORDER-" + currentTime + "-" + randomString; // "ORDER-<현재시간>-<랜덤문자열>"
    }
}