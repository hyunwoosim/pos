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
                .map(order ->{
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