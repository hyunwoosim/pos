


# TIL (Today I Learned)

## 📅 날짜: 01-23

---

## 1. 진행 상황 🛠️
TossPaymets의 요청을 받고 검증 후 승인까지 구현 완료.

- **프로젝트/기능명**: [결제 정보와 저장한 값이 맞는지 검증 후 결제 요청 성공하면 결제가 승인된다.]
- **진행한 작업**:
    - [O] 기능 개발: 

1. tossOrderId의 발급 방식을 변경하였다.
   - 원인 : UUID를 DB에 저장할때 생성하였는데 tossOrderId로 결제 정보를 찾아야 하는데 DB에 
    

---

## 2. 오류 및 해결 방법 ⚠️
작업 중에 발생한 오류와 이를 어떻게 해결했는지 기록합니다.

### 🔍 오류 1
- **오류 내용**: [결제 정보를 찾는 UUID 발급 방식 문제 발생]
- **원인 분석**:
    1. DB 저장 전 `.tossOrderId(UUID.randomUUID().toString())`로 `tossOrderId`를 발급하였는데 결제 정보를 tossOrderId로 찾기 때문에 DB저장 전에는 확인할 수가 없어 결제 검증을 할 수가 없다.
    2. UUID의 중복 문제
- **해결 방법**:
    1. 서비스에서 랜덤UUID로 tossOrderId를 만들어서 넘겨주기로 결정하였다.
    2. 중복문제 해결을 위해 현재시간 밀리초, 랜덤 문자를 사용하여 중복 방지
```java
      public class controller{
              // tossOrderId로 사용할 랜덤 UUID 생성기
         private String generateUniqueOrderId() {
            String currentTime = String.valueOf(System.currentTimeMillis()); // 현재 시간을 밀리초로 가져옴
            String randomString = UUID.randomUUID().toString().substring(0, 6); // 랜덤 문자열 일부만 사용 (예: 앞 6자리)
            return "ORDER-" + currentTime + "-" + randomString; // "ORDER-<현재시간>-<랜덤문자열>"
        }


    @GetMapping("/tossPay/checkout/{currentName}")
    public String GetTossPay(Model model, @PathVariable int currentName) {

        DiningTableDto currentOrder = diningTableService.findTableOrder(currentName);
        String tossOrderId = generateUniqueOrderId();
        model.addAttribute("currentOrder", currentOrder);
        model.addAttribute("getTotalDiningTablePrice", currentOrder.getTotalDiningTablePrice());
        model.addAttribute("tossOrderId", tossOrderId);

        return "/tossPay/checkout.html";
    }
        
      }
```


### 🔍 오류 2
- **오류 내용**: [발생한 오류에 대한 상세 설명]
- **원인 분석**: [오류가 발생한 이유]
- **해결 방법**:
    1. [해결 과정 1]
    2. [해결 과정 2]

---

## 3. 회고 📝
오늘 작업에 대한 회고와 느낀 점, 개선할 점 등을 작성합니다.

- **잘한 점**:
    - [어떤 점에서 효율적이었는지, 만족스러웠는지 기록]
- **아쉬운 점**:
    - [개선해야 할 부분, 다음에 더 잘할 수 있는 부분 기록]
- **앞으로의 계획**:
    - [다음 작업에서 적용할 계획 또는 학습할 주제]

---

## 🔗 참고 자료 📚
작업 중 참고한 자료 또는 링크를 정리합니다.

- [문서 링크나 블로그 포스트]
- [레퍼런스 코드나 관련 강의]

---



## 📅 날짜: 01-21

---

## 1. 진행 상황 🛠️
- TossPay API를 사용해서 결제시스템을 구현 중
  - 결제 요청을 할 때 결제 내역 검증을 위한 orderId,amount,payStatus 등을 저장 구현

- **프로젝트/기능명**: [결제 요청 전 검증을 위한 저장 구현]
- **진행한 작업**:
    - [O] 기능 개발:


1. TossPay에서 제시해주는 결제 흐름이다. 결제 요청전 결제금액 확인, 취소 등을 확인하기 위해 결제 정보를 저장하라고 제시한다.
      <img src="/READMEImages/TossPay결제 흐름.png"/>

2. PayMent Entity, Enum(PayType, PayStatus), PaymentRequestDto, TossWidgetController, TossWidgetService, tossPay템플릿 구현
3. PaymentRequestDto에서 저장하는 값을 Builder 패턴을 사용하여 amount 값 프론트에서 받아오고 TossPay Document의 규칙을 적용하여 OrderId는 UUID를 적용 시켰다.
    ```java
        @Getter
        @Setter
        @Builder
        public class PaymentRequestDto {
            // 결제 요청을 받을 Dto
            private PayStatus payStatus;
            private String tossOrderId;
            private int amount;
            private LocalDateTime requestedAt;
    
            public Payment toEntity() {
                return Payment.builder()
                    .payStatus(PayStatus.READY)
                    .tossOrderId(UUID.randomUUID().toString())
                 .totalAmount(amount)
                 .requestedAt(LocalDateTime.now())
                 .build();
            }
        }
    ```
4. POSTMAN에서 ResponseEntity.ok결과값 저장 성공이 잘 넘어 오고 있다.
<img src="READMEImages/TossPay결제%20검증용%20PostMan.png">

5. DB에도 잘 들어간다.
<img src="READMEImages/TossPay결제%20검증용%20DB.png">
---

## 🔗 참고 자료 📚
1. TossPay Document
   - [https://docs.tosspayments.com/guides/v2/payment-widget/integration]
---



# 📅 날짜: 12.16

---

## 1. 진행 상황 🛠️
Redis를 사용한 이메일 인증 기능 구현

- **프로젝트/기능명**: [이메일 인증 기능]
- **진행한 작업**:
    - [O] 기능 개발: 
      1. Redis와 SMTP 서버를 사용하여 이메일 인증 기능 구현
      2. 6자리의 랜덤 숫자 코드를 입력하여 인증
      3. Ajax를 사용한 비동기 처리

---

## 2. 오류 및 해결 방법 ⚠️
작업 중에 발생한 오류와 이를 어떻게 해결했는지 기록합니다.

### 🔍 오류 1
- **오류 내용**: [발생한 오류에 대한 상세 설명]
- **원인 분석**: [오류가 발생한 이유]
- **해결 방법**:
    1. [해결 과정 1]
    2. [해결 과정 2]

### 🔍 오류 2
- **오류 내용**: [발생한 오류에 대한 상세 설명]
- **원인 분석**: [오류가 발생한 이유]
- **해결 방법**:
    1. [해결 과정 1]
    2. [해결 과정 2]

---

## 3. 회고 📝
오늘 작업에 대한 회고와 느낀 점, 개선할 점 등을 작성합니다.

- **잘한 점**:
    - [어떤 점에서 효율적이었는지, 만족스러웠는지 기록]
- **아쉬운 점**:
    - [개선해야 할 부분, 다음에 더 잘할 수 있는 부분 기록]
- **앞으로의 계획**:
    - [다음 작업에서 적용할 계획 또는 학습할 주제]

---

## 🔗 참고 자료 📚
작업 중 참고한 자료 또는 링크를 정리합니다.

- [문서 링크나 블로그 포스트]
- [레퍼런스 코드나 관련 강의]

---

# 📅 날짜: 12-13

---

## 1. 진행 상황 🛠️
회원 가입 기능 완성

- **프로젝트/기능명**: [회원 가입 기능]
- **진행한 작업**:
    - [O] 기능 개발:
      1. @Valid를 사용하여 회원가입시 중복검사 기능을 구현 Id값으로 중복검사를 실행한다.
      2. BCryptPasswordEncoder를 사용하여 비밀번호를 암호화


---


=============================================================================
# 포스 단말기 구현

## 개요
음식점의 포스 단말기 시스템을 구현 중입니다. 주된 기능으로는 주문, 주문 취소, 재고 수량 확인, 결제 등이 포함됩니다.

## 12.13 진행 상황
### 회원 가입 기능 완성
- 중복 검사 및 패스워드 암호화 구현:
  - **BCryptPasswordEncoder**를 사용하여 비밀번호 암호화.

## 12.16 이메일 인증 기능 구현
- 레디스와 SMTP 서버를 사용하여 이메일 인증 기능을 완성.
- 6자리 랜덤 숫자 코드를 사용하여 이메일 인증 구현.
- Ajax를 이용한 비동기 처리로 이메일 전송과 유효성 검사를 처리.

### 처리 흐름
1. 사용자가 이메일을 입력하고 전송 버튼을 클릭.
2. `emailVerificationService.generateVerificationToken(email)`로 6자리 랜덤 코드 생성.
3. `emailSenderService.sendVerificationEmail(email, code)`로 이메일 전송.
4. 사용자가 받은 코드를 입력하고 `emailVerificationService.verifyToken(code)`로 유효성 검증.

## 12.18 아이템 구분 및 등록
- 아이템 카테고리를 구분하기 위해 `@DiscriminatorColumn(name = "category")` 사용.
- 추후 추가적인 컬럼을 사용할 계획이었으나, 현재는 **enum 타입**으로 구분 예정.
- 상품 등록 데이터는 정상적으로 들어가며, 이미지 처리에서 오류 발생:
  - 이미지 URL이 DB에 저장되지 않음.
  - 선택한 이미지 대신 기본 이미지만 보임.

### 이미지 관련 문제 해결
- **AWS S3** 연동하여 이미지를 저장.
- **Presigned URL**을 사용하여 이미지 저장 문제 해결.

## 12.19 메뉴 관련 기능 구현
### Admin용 메뉴판
- `ItemType`, 이름, 가격을 가져오는 쿼리 작성.
- 타입별로 그룹화하여 Map으로 전달.
- 스트림을 사용해 `groupingBy()`로 타입별 아이템 그룹화.

### USER용 메뉴판
- Admin과 동일한 방식으로 메뉴판 구현하였으며, **이미지 포함**.

## 12.20 트러블슈팅 및 구현
### Order와 Item의 관계
- OneToMany 관계에서 DB 부하를 줄이기 위해 중계 테이블 생성 고민.

### 상품 주문 UI
- 상품을 선택하면 이름, 수량, 가격이 나타나며, 총 수량과 총 가격도 표시.

## 12.24 DB 구조 개선
- **중계 테이블 생성**: `Order`와 `Item` 사이에 `OrderItem` 테이블 추가하여 DB 부담 경감.
- **테스트 코드 작성**: `OrderServiceTest`로 데이터 전송 확인 후 정상 작동.

## 12.25 고민 사항
### `orderItem`의 가격 칼럼에 대해 고민
- 아이템 가격을 저장할지, 종합 가격을 저장할지 결정.
- 종합 가격으로 저장하기로 최종 결정.

### DiningTable 엔티티 추가
- `diningTable` 엔티티를 만들어 주문과 테이블을 연결.
- `name` 컬럼을 추가하여 1~10까지의 고정된 테이블 이름을 사용.

### DiningTableComponent 로직
- 서버 시작 시 1~10까지의 테이블 이름을 고정값으로 설정하는 로직을 추가:
  ```java
  @Component
  @Transactional
  public class DiningTableComponent implements CommandLineRunner {
      private final DiningTableRepository diningTableRepository;

      public DiningTableComponent(DiningTableRepository diningTableRepository) {
          this.diningTableRepository = diningTableRepository;
      }

      @Override
      public void run(String... args) throws Exception {
          for (int i = 1; i <= 10; i++) {
              if (!diningTableRepository.existsByName(i)) {
                  diningTableRepository.save(new DiningTable(i));
              }
          }
      }
  }

### 포스 단말기 기능 구현
- 포스기에서 실제 테이블을 선택하면 해당 테이블에서 메뉴를 선택할 수 있도록 구현 중.
- 주문이 완료되면, 해당 테이블에 메뉴명과 가격이 표시되도록 구현을 진행 중이다.

### DiningTable을 분리
- `OrderController`, `OrderService`에 기능을 구현했는데 유지보수가 편하게 `DiningController`, `DiningService`로 분리했다.
- POS 테이블을 누르면 그 테이블 번호가 넘어가게 만들고 DB에 잘 넣어지는 것까지 확인했다.


## 12.27
### 고민사항
- diningTable을 찾는 방식이 `int name` 값으로 찾고 있는데 이게 문제인 거 같다.
- 근데 여기저기 기능이 섞여 있어 손 볼 게 많다.
- 뭔가 많이 꼬인 거 같은 느낌이 들고 있다. 머리를 식힌 후 다시 해결해야겠다.


## 12.30 
- 일단 직접 테이블에서 호출해서 데이터를 찾아오지 않기위해 dto를 사용하였다.
- 거기에 불필요한 데이터를 넣지 않기위해 `Strem`을 사용하여 dto에 데이터들을 넣었고
- 화면에 출력까지 성공했다.
### 고민사항
1. 지금은 `totalPrice`에 대한 고민 중이다. 화면에서 주문할 때 `price * count`를 해서 `totalPrice`를 보여주는데
- 이미 합계 계산을 했는데 데이터베이스에 넣을 때 `합계 로직`을 구현할 필요가 없을 거 같아서 안 했는데
- 기능 분리를 위해 만들어야 할 거 같다는 생각이 든다.
2. 화면에 `totalPrice`를 보여주고 싶은데 `하나의 Order`에 `여러 개의 OrderItem`이 있을 수도 있어서
- 그 데이터베이스 안에 있는 전체 `OrderItem`의 `totalPrice`를 서비스에 합쳐서 전송하려고 구현 중인데 어렵다.

## 12.31
- 서비스에서 form을 호출하면 각각의 Dto에서 데이터를 변환하여 넘겨준다.
1. DiningTableService
    ```java
    
    
      public DiningTableDto findTableOrder(int name) {
    
            DiningTable byName = diningTableRepository.findByName(name);
            DiningTableDto from = DiningTableDto.from(byName);
            return  from;
        }
    ```
2. DiningTableDto 
    ```java
    
    public static DiningTableDto from(DiningTable diningTable) {
            List<OrderDto> orderDtos = diningTable.getOrders().stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    
            // 모든 Order의 총 금액 합산
            int totalDiningTablePrice = orderDtos.stream()
                .mapToInt(OrderDto::getTotalOrderPrice)
                .sum();
    
            System.out.println("########## DiningTableDto ###############");
            System.out.println("totalDiningTablePrice = " + totalDiningTablePrice);
            System.out.println("########## DiningTableDto ###############");
    
    
            return new DiningTableDto(diningTable.getId(), diningTable.getName(), orderDtos, totalDiningTablePrice);
        }
    ```
3. OrderDto
    ```java
        public static OrderDto from(Order order) {
            // Order에서 OrderItemDto 리스트를 생성
            List<OrderItemDto> orderItemDtos = OrderItemDto.fromList(order.getOrderItems());
    
    
    
                // OrderItem의 총 가격 합산
                int totalOrderPrice = orderItemDtos.stream()
                    .mapToInt(orderItemDto -> orderItemDto.getTotalPrice() * orderItemDto.getCount())
                    .sum();
    
            System.out.println("########## OrderDTO ###############");
            System.out.println("totalOrderPrice = " + totalOrderPrice);
            System.out.println("########## OrderDTO ###############");
    
    
            // 변환된 데이터를 사용하여 OrderDto 객체를 생성하고 반환
            return new OrderDto(order.getId(), order.getCreated(), orderItemDtos, totalOrderPrice);
        }
    ```
4. OrderItemDto
    ```java
    
     public static OrderItemDto from(OrderItem orderItem) {
    
            System.out.println("########## OrderItemDto from #########");
            System.out.println("orderItem.getItem().toString() = " + orderItem.getItem().toString());
            System.out.println("########## OrderItemDto from #########");
            // 단일 OrderItem을 변환
            ItemDto itemDto = ItemDto.from(orderItem.getItem());
            return new OrderItemDto(orderItem.getId(), orderItem.getTotalPrice(), orderItem.getCount(), itemDto);
        }
    
        public static List<OrderItemDto> fromList(List<OrderItem> orderItems) {
            // OrderItem 리스트를 변환
    
            System.out.println("########## OrderItemDto List #########");
            System.out.println(orderItems.stream()
                                   .map(OrderItemDto::from)
                                   .collect(Collectors.toList()));
            System.out.println("########## OrderItemDto List #########");
            return orderItems.stream()
                .map(OrderItemDto::from)
                .collect(Collectors.toList());
        }
        // ==  테스트 코드를 위해 생성//
        public OrderItemDto(Long itemId, int totalPrice, int count) {
            this.itemId = itemId;
            this.totalPrice = totalPrice;
            this.count = count;
        }
    ```
5. ItemDto
    ```java
      public static ItemDto from(Item item) {
            return new ItemDto(item.getId(), item.getName(), item.getPrice());
        }
    ```
<img src="READMEImages/종합가격 구현한 스크린샷.png">

### 구현단계이기 때문에 모든 데이터를 표시하고 있다.


## 1.6
- 폼에서 호출하던 걸 다 갈아엎었다.
```java
public DiningTableDto findTableOrder(int name) {

    DiningTable byName = diningTableRepository.findByName(name);

    DiningTableDto diningTableDto = new DiningTableDto();
    diningTableDto.setId(byName.getId());
    diningTableDto.setName(byName.getName());

    List<OrderDto> orders = byName.getOrders().stream()
        .map(order -> {
            OrderDto orderDtos = new OrderDto();
            orderDtos.setId(order.getId());
            System.out.println("###############DiningTableService################");
            System.out.println("orderDtos.getId() = " + orderDtos.getId());
            System.out.println("###############DiningTableService################");

            List<OrderItemDto> items = order.getOrderItems().stream()
                .map(item -> new OrderItemDto(
                    item.getId(),
                    item.getItem().getId(),
                    item.getOrder().getId(),
                    item.getItem().getName(),
                    item.getItem().getPrice(),
                    item.getCount()
                ))
                .collect(Collectors.toList());
            orderDtos.setOrderItems(items);

            return orderDtos;

        })
        .collect(Collectors.toList());
    diningTableDto.setOrders(orders);
    return diningTableDto;
}
```
- 스트림을 사용하여 필요한 데이터들을 넣어서 model에 담아서 전달하였다.
- 그렇게 하여 주문서에 한 번에 뜨게 만들었다
- 그리고 주문 버튼 하나로 주문, 업데이트가 가능하게 만들었다.
```java

 @Transactional
    public void orderAdd(OrderDto orderDto) {
        // orderId가 존재하면 기존 주문을 업데이트, 없으면 새로운 주문을 생성
        Order order = null;

        System.out.println("#######OrderService 11 #######");
        System.out.println("orderDto = " + orderDto.getId());
        System.out.println("#######OrderService 11 #######");

        if (orderDto.getId() != null) {

            System.out.println("#######OrderService 22 #######");
            System.out.println("orderDto = " + orderDto.getId());
            System.out.println("#######OrderService 22 #######");

            // 기존 주문이 있을 경우, orderId로 주문을 찾아서 업데이트
            order = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

            // 기존 주문을 업데이트
            updateExistingOrder(order, orderDto);
        } else {

            System.out.println("#######OrderService 33 #######");
            System.out.println("orderDto = " + orderDto.getId());
            System.out.println("#######OrderService 33 #######");
            // 새로운 주문이면 새로운 주문 생성
            DiningTable diningTable = diningTableRepository.findByName(orderDto.getDiningName());
            order = new Order();
            order.addDining(diningTable);

            // 새로운 주문 항목 추가
            Order finalOrder = order;
            orderDto.getOrderItems().forEach(orderItemDto -> {
                Item item = itemRepository.findById(orderItemDto.getItemId()).orElseThrow();
                OrderItem orderItem = new OrderItem();
                orderItem.addOrderItem(item, orderItemDto.getTotalPrice(), orderItemDto.getCount());
                finalOrder.addOrderItem(orderItem);
            });

            // 새로운 주문 저장
            orderRepository.save(order);
        }
    }
```
- orderId로 새로운 주문인지 기존의 주문인지 유효성 검사를 하였고
- order에는 여러 개의 OrderItem이 있는 경우가 있을 수 있음으로
- 업데이트는 OrderItemId를 기준으로 업데이트하였다.


<img src="READMEImages/주문업데이트 구현.png">

## 1.7
### 오류 발견
- order 수정중 하던 도중 기존 주문에 수정과 새로운 주문을 같이 했을때 
- 기존 주문이 새로운 주문으로 함께 입력되는 중복으로 주문 되는 오류가 발생하였다.
- orderId기준으로 기존 주문과 새로운 주문으로 유효성검사를 하였는데
- 밑에 처럼 넘어 오기 때문에 무조건 새로운 주문으로 되는 중 있었다.
```java
디티오OrderDto{id=null, diningName=3, created=null, orderItems=[
    OrderItemDto{orderItemId=1702, itemId=52, orderId=1752, totalPrice=9000, count=1, name='null', price=0, itemDtos=null}, 
  OrderItemDto{orderItemId=1703, itemId=102, orderId=1752, totalPrice=10000, count=2, name='null', price=0, itemDtos=null}, 
  OrderItemDto{orderItemId=1704, itemId=202, orderId=1752, totalPrice=1000, count=8, name='null', price=0, itemDtos=null}, 
  OrderItemDto{orderItemId=null, itemId=252, orderId=null, totalPrice=7000, count=5, name='null', price=0, itemDtos=null}]}
```
### 해결
1. add, update 분리
2. 컨트롤러에서 list로 만들어 null값을 기준으로 새로운 주문과 기존 주문을 구분하였다.
    ```java
     public ResponseEntity<Map<String, String>> addOrder(@RequestBody OrderDto orderDto) {
        List<OrderItemDto> addItems = new ArrayList<>();
        List<OrderItemDto> updateItems = new ArrayList<>();
    
        // orderDto의 orderItems를 순회하여 분리
        for (OrderItemDto orderItem : orderDto.getOrderItems()) {
            if (orderItem.getOrderId() == null) {
                addItems.add(orderItem);  // orderId가 null인 항목은 추가
            } else {
                updateItems.add(orderItem);  // orderId가 null이 아닌 항목은 업데이트
            }
          // 분리된 addItems와 updateItems를 각각 처리
          if (!addItems.isEmpty()) {
    
            // 새로운 주문 항목을 추가하는 서비스 호출
            orderService.orderAdd(orderDto, addItems);
          }
    
          if (!updateItems.isEmpty()) {
    
            // 기존 주문 항목을 업데이트하는 서비스 호출
            orderService.orderUpdate(orderDto, updateItems);
          }
    
          Map<String, String> response = new HashMap<>();
          response.put("redirectUrl", "/orderTable");
          return ResponseEntity.ok(response);
        }
    
    }
    ```
3. list를 활용하여 orderItemId를 기준으로 업데이트 한 후 order를 호출하여 변경하였다.
    ```java
       @Transactional
        public void orderUpdate(OrderDto orderDto, List<OrderItemDto> updateItems) {
    
    
            System.out.println("####### orderUpdate #######");
            System.out.println("orderDto = " + orderDto.getId());
            System.out.println("####### orderUpdate #######");
    
            // 1. 업데이트 항목에 해당하는 orderItemId들을 기준으로 각 항목을 업데이트
            updateItems.forEach(orderItemDto -> {
                // updateItems에서 각 항목을 순차적으로 처리
                OrderItem existingOrderItem = orderItemRepository.findById(orderItemDto.getOrderItemId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 항목입니다."));
    
                // 기존 OrderItem의 변경 감지를 트리거하기 위해 값 설정
                existingOrderItem.updateOrderItem(orderItemDto.getTotalPrice(), orderItemDto.getCount());
    
                // 해당 OrderItem을 포함하는 Order를 찾아서 변경된 값을 반영
                Order order = existingOrderItem.getOrder();
    
                System.out.println("####### orderUpdate22 #######");
                System.out.println("order = " + order);
                System.out.println("####### orderUpdate 22#######");
    
                orderRepository.save(order);  // 변경된 주문을 저장 (변경 감지)
            });
    
        }
    ```

## 1.10
### 기능 추가
- orderItem을 삭제하는 기능을 추가하였다.
- 엔티티 설계를 잘못한 느낌이 크게 든다.
- 일단 지금은 ajax로 json으로 받아올때 cancelOrderItemId를 같이 받아오기로 하여 삭제 기능을 처리하였다.
```
 const orderDto = {
      diningName: tableName,
      orderItems: Object.keys(orderSummary).map(itemId => {
        const item = orderSummary[itemId];
        console.log(currentOrder.orders.id);
        console.log("orderSummary itemId:", item.orderId);  // 여기서 order.id 확인
        return {
           orderId: item.orderId,
          orderItemId: item.orderItemId,
          itemId: itemId,
          count: item.quantity,
          totalPrice: item.price
        };
      }),
      cancelOrderItemId: cancelOrderItemId
    };
```
### Controller
```java
@PostMapping("/order/add")
public ResponseEntity<Map<String, String>> addOrder(@RequestBody OrderDto orderDto) {
// 주문 삭제
    if (orderDto.getCancelOrderItemId() != null) {
        System.out.println("########### 오더 컨트롤러 delete #############");
        System.out.println("orderDto.getCancelOrderItemId() = " + orderDto.getCancelOrderItemId());
        System.out.println("########### 오더 컨트롤러 delete#############");
        System.out.println("===============================================");
        orderService.orderItemDelete(orderDto);
    }
}
```
### Service
```java
 @Transactional
    public void orderItemDelete(OrderDto orderDto){
        orderDto.getCancelOrderItemId().forEach(orderItemId ->{
            orderItemRepository.deleteById(orderItemId);
            System.out.println("######## OrderDelete ###########");
            System.out.println("orderItemId = " + orderItemId + "삭제");
            System.out.println("######## OrderDelete ###########");
            System.out.println("======================================");
        });
    }
```
- orderItem이 아주 잘 삭제되었다.
### 오류 발생
- orderItem을 다 취소한 경우 orderItem은 다 삭제되지만 연관 관계로 연결되어있는 order가 삭제되지 않는 오류가 발생하였다.

### 해결
```
orderId=[orderItemId,orderItemId]
```
- 이런식으로 Map<Long, List<Long>>형식으로 데이터를 받아와
```java
public ResponseEntity<Map<String, String>> addOrder(@RequestBody OrderDto orderDto) {
    // 주문 삭제
    if (orderDto.getCancelOrderId() != null) {
        System.out.println("########### 오더 컨트롤러 delete #############");
        System.out.println("orderDto.getCancelOrderItemId() = " + orderDto.getCancelOrderId());
        System.out.println("########### 오더 컨트롤러 delete#############");
        System.out.println("===============================================");
        orderService.orderItemDelete(orderDto);
    }
}
```
- 유효성 검사를 거친 뒤
```java

    @Transactional
    public void orderItemDelete(OrderDto orderDto){
        orderDto.getCancelOrderId().forEach((orderId, orderItemIds) -> {
            orderItemIds.forEach(orderItemId -> {

                orderItemRepository.deleteById(orderItemId);

                System.out.println("######## orderItemId ###########");
                System.out.println("orderItemId = " + orderItemId + "삭제");
                System.out.println("######## orderItemId ###########");
                System.out.println("======================================");
            });

            // 영속성 컨텍스트를 DB에 반영
            orderItemRepository.flush();

            //order를 조회
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();

                System.out.println("######## OrderDelete ###########");
                System.out.println("order = " + order);
                System.out.println("######## OrderDelete ###########");
                System.out.println("======================================");

                if (order.getOrderItems().isEmpty()) {
                    orderRepository.deleteById(orderId);
                    System.out.println("######## OrderDelete ###########");
                    System.out.println("orderId = " + orderId + "삭제");
                    System.out.println("######## OrderDelete ###########");
                    System.out.println("======================================");
                }
            }
        });

    }
```
# 중요!!!! 
- 먼저 orderItem을 지워주고 flush로 db에 바로 반영해 준다.
- 이유는 만약 orderItem을 모두 지우는 상황이 오면 연관관계인 order도 지워야 하기 때문이다.
- 하지만 모든 작업이 끝나기 전에는 영속성 컨텍스트로 남아있기 때문에 DB에 반영이 안 될 수도 있기 때문에 flush()를 사용하는 것이다.

## 오류 발생
- 신규 주문, 기존 주문 업데이트, 주문 삭제를 동시에 진행하면 기능이 동작은 하지만 변경되지 않는다.
- 이유는 아마 동시성의 문제인 거 같다.
- 3가지 부분의 유효성 검사를 다시 손봐야 할 거 같다.

## 1.13
- 신규 주문, 기존 항목 업데이트, 삭제 따로따로 기능은 잘 실행되었지만
- 동시에 기능을 실행하면 삭제가 안 되는 오류가 발생하였다.
- 동시성의 문제였다.
### 해결
```java
 @PostMapping("/order/add")
    public ResponseEntity<Map<String, String>> addOrder(@RequestBody OrderDto orderDto) {


        if (orderDto.getCancelOrderId() != null) {
            orderService.orderItemDelete(orderDto);
        }

        if (!orderDto.getOrderItems().isEmpty()) {
            List<OrderItemDto> addItems = new ArrayList<>();
            List<OrderItemDto> updateItems = new ArrayList<>();
            splitOrderItems(orderDto, addItems, updateItems);

            if (!addItems.isEmpty()) {
                orderService.orderAdd(orderDto, addItems);
            }
            if (!updateItems.isEmpty()) {
                orderService.orderUpdate(orderDto, updateItems);
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/orderTable");
        return ResponseEntity.ok(response);
    }
```
- 삭제 기능부터 유효성 검사를 시작하였다.
- 삭제할 게 있으면 삭제한 후 flush로 영속성컨텍스에 있는 삭제 항목을 먼저 처리한다.
- 그 후 신규 주문과 업데이트의 분리 작업을 시작한다.
```java
// 유효성 검사를 통해 addItems,updateItems에 뎉이터를 넣고 있다.
    private void splitOrderItems(OrderDto orderDto, List<OrderItemDto> addItems, List<OrderItemDto> updateItems) {
        for (OrderItemDto orderItem : orderDto.getOrderItems()) {
            if (orderItem.getOrderId() == null) {
                addItems.add(orderItem);
            } else {
                updateItems.add(orderItem);
            }
        }
    }
```
- 유효성 검사를 통해 신규 주문 리스트와, 업데이트 리스트를 분리 후 각각의 서비스를 호출한다.

## 1.14
### 결제 시스템 구현중
- 토스 poss를 벤치 마킹하였기 때문에 결제 시스템도 toss Pay를 사용하기로 하였다.
- toss Pay의 결제 기능은 구현은 개발 문서에 아주 자세하게 설명되어 있고 샌드박스를 사용할 수 있어서 보다 편하게 구현이 가능하다.
- 출처 : https://docs.tosspayments.com/guides/v2/payment-widget/integration?backend=java
<img src="READMEImages/tosspay구현중.png">
- 이제 데이터를 잘 가공하여서 다듬으면 된다.



TIL 양식
```
# TIL (Today I Learned)

## 📅 날짜: YYYY-MM-DD

---

## 1. 진행 상황 🛠️
오늘 어떤 작업을 진행했는지 정리합니다.

- **프로젝트/기능명**: [여기에 작업한 프로젝트나 기능 이름을 작성]
- **진행한 작업**:
    - [ ] 기능 개발: [구현한 기능 설명]
    - [ ] 테스트 코드 작성: [테스트한 내용]
    - [ ] 리팩토링: [리팩토링한 부분과 이유]
    - [ ] 문서 작성: [작성한 문서 또는 기록]

---

## 2. 오류 및 해결 방법 ⚠️
작업 중에 발생한 오류와 이를 어떻게 해결했는지 기록합니다.

### 🔍 오류 1
- **오류 내용**: [발생한 오류에 대한 상세 설명]
- **원인 분석**: [오류가 발생한 이유]
- **해결 방법**:
    1. [해결 과정 1]
    2. [해결 과정 2]

### 🔍 오류 2
- **오류 내용**: [발생한 오류에 대한 상세 설명]
- **원인 분석**: [오류가 발생한 이유]
- **해결 방법**:
    1. [해결 과정 1]
    2. [해결 과정 2]

---

## 3. 회고 📝
오늘 작업에 대한 회고와 느낀 점, 개선할 점 등을 작성합니다.

- **잘한 점**:
    - [어떤 점에서 효율적이었는지, 만족스러웠는지 기록]
- **아쉬운 점**:
    - [개선해야 할 부분, 다음에 더 잘할 수 있는 부분 기록]
- **앞으로의 계획**:
    - [다음 작업에서 적용할 계획 또는 학습할 주제]

---

## 🔗 참고 자료 📚
작업 중 참고한 자료 또는 링크를 정리합니다.

- [문서 링크나 블로그 포스트]
- [레퍼런스 코드나 관련 강의]

---
```