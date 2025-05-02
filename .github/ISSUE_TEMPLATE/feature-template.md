---
name: Feature Template
about: 기능 템플릿
title: ''
labels: ''
assignees: goodperiodt

---

## Mockito 기반 단위 테스트 작성 - UserServiceImplTest.java

### ToDo
- [x] 테스트 환경 구성
  - @ExtendsWith(MockitoExtension.class) 적용
  - @Mock 객체 선언
    - UserValidator
    - UserRepository
    - PasswordEncoder
- [x] signUp() 기능 테스트
  - [x] 정상 케이스 - 회원가입 성공
    - 조건
      - 유효한 SignUpRequest 객체: 이메일, 비밀번호, 닉네임, 권한, 프로필 이미지
      - Mocking 객체 동작 정의
        - 요청 객체 중복 검사(이메일/닉네임)시, 예외가 발생하지 않는다.
        - 요청 객체의 비밀번호 암호화시, 암호화된 비밀번호를 반환한다.
        - 요청 객체로부터 생성된 유저 엔터티를 db에 저장시, 해당 유저를 반환한다.
    - 동작 - signUp() 호출
    - 결과 - 요청 객체의 이메일과 반환된 이메일이 일치한다.
  - [x] 예외 케이스
    - 이메일 중복시 예외(UserException) 발생
      - 조건: 요청 객체 중복 검사시 UserException 예외 발생
      - 동작: signUp() 호출
      - 결과: 예외 메시지 예상 값과 실제 값이 일치한다.
        - assertEquals("이미 존재하는 이메일입니다.", e.getMessage());