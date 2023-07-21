# springboot_project
Springboot 프레임워크를 통해 진행한 실습입니다.
카페의 리뷰를 작성하고 공유할 수 있는 사이트를 기획하고 작성했습니다.
<br>실습 과정에서 아래 서적의 코드를 변경하고 활용했습니다.

> 서적 : 백견불여일타 스프링 부트 쇼핑몰 프로젝트 with JPA

## 기능
- 헤더(navbar)
- 회원가입 / 로그인 및 로그아웃
- 리뷰 등록 및 수정 기능
- 리뷰 검색(Querydsl 활용)
- 상세 리뷰 조회

추가로 클라우드 서비스를 이용하여 배포, 도메인을 연결하는 과정을 실습했습니다.

## 구조

### Constant
- **DrinkType** : COFFEE, SMOOTHIE, SHAKE, DESSERT, ETC
- **Role** : USER, ADMIN

### Entity
- **Drink** : 리뷰
  - **id**(mapping: drinkId) / cafeNm / menuNm / rating / drinkDetail / drinkType
  <br>
- **DrinkImg** : 리뷰 이미지
  - **id** / imgName / oriImgName / imgUrl / repimgYn(대표 이미지 여부)
  - Join -> drink_id
  <br>
- **Member** : 회원
  - **id** / name / email(unique) / password / address / nickname / role
<br>

### dto
- **DrinkFormDto** : 리뷰 데이터 전달
- **DrinkImgDto** : 이미지 데이터
- **DrinkSearchDto** : 리뷰 조회 조건
- **ExploreDrinkDto** : 탐색 페이지 (검색 X)
- **MemberFormDto** : 가입 정보
  - 유효값 검증 어노테이션
<br>

### Controller
- **DrinkController**: 등록 및 수정 페이지 접근
  - ```/upload```: 등록
  - ```/upload/{drinkId}```: 수정
  - ```/search```, ```/search/{page}```: 조회한 리뷰 데이터를 화면에 전달
  - ```/item/{drinkId}```: 상세 페이지 이동
  <br>
- **MemeberController**: 회원가입 및 로그인 페이지 접근
  - ```/new```: 회원가입
  - ```/login```: 로그인
  <br>
- **MainController**
  - ```/```: 메인 페이지
  - ```/about```: 소개 페이지로 이동
  - ```/explore```: 검색 페이지로 이동
<br>

### Repository
#### 데이터베이스에 저장<br> JpaRepository 상속

- **DrinkRepository**
  - DrinkRepositoryCustom 상속
- **DrinkImgRepository**
- **MemberRepository**
  
**Search -> Querydsl 사용**
- **DrinkRepositoryCustom**: 사용자 정의 인터페이스
- **DrinkRepositoryCustomImpl**: 사용자 정의 인터페이스 구현
  - method: getAdminDrinkPage

### Service
- **DrinkImgService** : 이미지 정보 저장
- **DrinkService** : 리뷰 등록
  - getAdminDrinkPage: 조회 조건, 페이지 정보 -> 데이터 조회
- **FileService** : 파일 처리
  - uploadFile / deleteFile
- **MemberService**
  - implements UserDetailsService (loadUserByUsername method 오버라이딩)


