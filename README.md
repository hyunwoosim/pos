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