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





=============================================================================

# 포스 단말기 구현

## 개요
- 가게 음식점에 있는 포스 단말기를 생각하면 된다.
- 주문, 주문취소, 재고수량 확인, 결제 등을 구현할 예정이다.


## 12.13
- 회원 가입 완성
- 중복 검사, 패스워드 암호화

- BCryptPasswordEncoder를 사용하여 비밀번호를 암호화 하였다.

## 12. 16
- 레디스와 STMP서버를 사용하여 이메일 인증 기능 완성
- 토큰 방식을 사용하지 않고 6자리 랜덤 숫자로 유효 코드를 보내는 기능으로 만들었다.
- 이유는 코드를 가져와 유효성 검사를 하고싶었기 때문이다.
- Ajax를 통해 비동기 처리로 메일 전송부터, 유효성 검사까지 보다 처리되게 만들었다.


- 사용자가가 이메일을 적고 전송 버튼을 누르면 컨트롤러에서 RequestPram으로 이메일을 받아
- emailVerificationService.generateVerificationToken(email) 호출하여 6자리 랜덤 숫자 코드를 만들어
- emailSenderService.sendVerificationEmail(email, code); 이메일을 전송한다.
- 그 후 사용자가 이메일에서 받은 코드를 가져와 
- emailVerificationService.verifyToken(code) 유효성 검증을 한다.

## 12.18
- 아이템에서  차가운 음료, 뜨거운 음료 등등을 구분하기 위해 
<b>@DiscriminatorColumn(name = "category")</b> 사용하였다.
- 사용하려고 했지만 지금 당장 추가적인 컬럼이 없을꺼 같기 때문에 enum 타입을 사용해서 구분할 예정이다.

- 상품 등록 데이터들은 다 잘 들어간다.
- 이미지를 등록할때 처음에 디폴트 이미지가 보이고 그 다음 이미지를 등록하면 그 이미지가 나오게 할 것이다.
- 그래서 지금 그걸 구현중이다 나중에는 S3서버를 사용해서 거기에 이미지를 저장할 예정이다.
- 근데 지금 이미지쪽에서 오류가 발생하였다.
1. 이미지 URL이 DB에 저장이 안됨 
2. 파일에서 선택한 이미지가 보이지 않고 default이미지만 보이는중이다. 

## 12.19
- AWS S3에 연동하여 이미지를 저장하기로 하였다. Presigned URL을 사용하여 문제를 해결하였다. 
- S3 서버에 이미지 파일을 저장하여 서버에 부담감을 줄였다.
- 포스기에서 admin이 주문 받은 메뉴를 찍게 하기위해 구현하였다.
- 일단 admin에게는 사진이 필요 없고 메뉴명과 가격만 있으면 되기 때문에 사진을 제외한 
- ItemType,이름,가격을 가져오기 위해 새로운 Query를 만들었다. 
- 엔티티에서 DTO로 받아서 가져온다
- 타입별로 보고싶기 때문에 고민한 결과 MAP담아 그룹으로 보내기로 결정했다.
```
 List<ItemDto> itemDto = itemService.adminMenu();

    Map<ItemType, List<ItemDto>> itemTypeListMap = itemDto.stream()
        .collect(Collectors.groupingBy(ItemDto::getItemType));
```
- list를 스트림으로 변환하여 타입별로 그룹화 시켜서 map 담아서 보낸다.
- 결과 아주 깔끔하고 이쁘게 나왔다.
  - 지금은 대략적으로 CSS로 꾸몄지만 나중에 React를 사용해서 꾸밀 예정이다.

- <b>USER용 메뉴판</b>도 완성하였다. USER메뉴판은 사진을 넣었다. 
- 방식은 위에 <b> Admin메뉴판</b>과 같은 방식을 사용하였다. 

## 12.20
### 트러블 슈팅?
- order에 저장하는 방식에 고민이 생겼다. order 와 Item은 지금 OneToMany 관계인데
- order 하나에 여러 개의 아이템이 저장될 수도 있기 때문에 그러면 DB에 부담이 크기 때문에
- 어떤 방식으로 저장할지 고민 중이다.
  - 중계 테이블을 만들지 , 그냥 저장할지 

### 구현
- 상품을 누르면 그 상품 이름,수량,가격이 뜨고 밑에는 총 수량, 총 가격이 뜨게 구현하였다.
- 바닐라 스크립트로 작성했다.


## 12.24
- order와 item 사이에 중계 테이블로 Order Item 테이블을 생성하였다.
- DB 부담을 줄였다. Member, order, Order Item,item 테이블 4개가 연결되어 있기 때문에
- OrderServiceTest를 만들어 Test 코드 작성 후 테스트 진행
- 성공적으로 데이터들이 잘 넘어갔다.

## 12.25
- order의 데이터가 잘 들어간다.
### 고민
- orderItem의 column total_price에 아이템의 가격을 넣을지 종합 가격을 넣을지 고민이다.
- 일단 지금 방식은 item column처럼 가격을 저장하고 있는데 중복된다 
- 고민중이다.

### 해결
- 종합가격으로 넣기로 결정했다.

- member테이블과 연결하는 대신 diningTable 엔티티를 만들어서 테이블과 연결하기로 결정했다.

## 12.26
- diningTable 엔티티 서버를 시작할 때 자동으로 10개의 고정 테이블을 생성하는 로직을 추가하였다.
- 처음에 id만 있었는데 지우고 생성하면 id 값이 1부터 시작하지 않고 랜덤적으로 생성되기 때문에
- diningTable id 로 찾을 때 문제가 생길 거 같아서 name컬럼을 추가하여 1~10까지 고정적인 값으로 만들었다.
```
@Component
@Transactional
public class DiningTableComponent implements CommandLineRunner {

    private final DiningTableRepository diningTableRepository;

    public DiningTableComponent(DiningTableRepository diningTableRepository) {
        this.diningTableRepository = diningTableRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <=10; i++) {
            if (!diningTableRepository.existsByName(i)) {
                diningTableRepository.save(new DiningTable(i));
            }
        }
    }
}
```
- 서버가 시작하면 실행되는 로직을 생성하였다. 리포지토리에서 1~10까지의 이름이 있으면 실행되고
- 없으면 실행되지 않는다.

- 지금은 포스기에서 실제 테이블을 누르면 메뉴를 선택할 수 있고
- 주문하면 테이블에 메뉴와 가격이 뜨게 하려고 구현 중이다.

==============================================================================