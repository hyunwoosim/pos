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