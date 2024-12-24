package com.mulook.pos.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mulook.pos.Service.OrderService;
import com.mulook.pos.dto.OrderDto;
import com.mulook.pos.dto.OrderItemDto;
import com.mulook.pos.entity.Item;
import com.mulook.pos.entity.Member;
import com.mulook.pos.entity.Order;
import com.mulook.pos.repository.ItemRepository;
import com.mulook.pos.repository.MemberRepository;
import com.mulook.pos.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private OrderDto orderDto;


    @Test
    public void orderAddTest() throws Exception {

        MockitoAnnotations.openMocks(this);

        // Given: memberRepository와 itemRepository에서 반환할 데이터를 설정
        Member member = new Member();
        member.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(100);

        orderDto = new OrderDto();
        orderDto.setMemberId(1L);
        orderDto.setCreated(LocalDateTime.now());
        orderDto.setOrderItems(Arrays.asList(
            new OrderItemDto(1L, 100, 2)  // 아이템 ID 1, 총 가격 100, 수량 2
        ));

        // memberRepository와 itemRepository에서 mock 데이터를 반환하도록 설정
        when(memberRepository.findById(orderDto.getMemberId())).thenReturn(Optional.of(member));
        when(itemRepository.findById(orderDto.getOrderItems().get(0).getItemId()))
            .thenReturn(Optional.of(item));

        // When: orderService의 orderAdd 메서드 호출
        orderService.orderAdd(orderDto);

        // Then: orderRepository의 save 메서드가 한 번 호출되었는지 검증
        verify(orderRepository, times(1)).save(any(Order.class));


        System.out.println("Member ID: " + member.getId());
        System.out.println("Item ID: " + item.getId());
        System.out.println("item.getName() = " + item.getName());
        System.out.println("OrderDto memberId: " + orderDto.getMemberId());
        System.out.println("orderDto = " + orderDto.getCreated());

        orderDto.getOrderItems().forEach(orderItemDto -> {
            System.out.println("orderItemDto.getItemId() = " + orderItemDto.getItemId());
            System.out.println("orderItemDto.getItemId() = " + orderItemDto.getCount());
            System.out.println("orderItemDto.getTotalPrice() = " + orderItemDto.getTotalPrice());
        });

    }
}