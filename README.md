# Spring Security 회원인증 시스템 연습용 습작

Spring Boot와 Spring Security를 활용한 사용자 인증 및 권한 관리 시스템입니다.

## 주요 기능

- **사용자 회원가입 및 로그인**
- **역할 기반 접근 제어 (RBAC)**
- **비밀번호 암호화 (BCrypt)**
- **세션 기반 인증**
- **다중 권한 관리 (USER, MANAGER, ADMIN)**

## 기술 스택

- **Backend**: Spring Boot, Spring Security, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 4
- **Database**: JPA/Hibernate 호환 데이터베이스
- **Build Tool**: Maven/Gradle
- **Java Version**: 17+

## 프로젝트 구조

```
src/main/java/com/example/security/
├── SecurityApplication.java           # 메인 애플리케이션 클래스
├── config/
│   └── SecurityConfig.java          # Spring Security 설정
├── controller/
│   ├── LoginController.java         # 로그인 컨트롤러
│   └── MemberController.java        # 회원 관리 컨트롤러
├── entity/
│   ├── Member.java                  # 회원 엔티티
│   ├── Role.java                    # 권한 엔티티
│   └── CustomerMember.java          # Spring Security UserDetails 구현체
├── repository/
│   ├── MemberRepository.java        # 회원 레포지토리
│   └── RoleRepository.java          # 권한 레포지토리
└── service/
    ├── MemberService.java           # 회원 서비스
    ├── RoleService.java             # 권한 서비스
    ├── CustomUserDetailsService.java # 사용자 세부정보 서비스
    └── LoginService.java            # 로그인 서비스

src/main/resources/templates/
├── list.html                        # 메인 페이지
└── register.html                    # 회원가입 페이지
```

## 보안 설정

### 접근 권한 설정
- `/api/**`: 인증 필요
- `/book/**`: 인증 필요
- 기타 모든 경로: 허용

### 로그인 설정
- 로그인 페이지: `/ui/list`
- 로그인 처리 URL: `/login`
- 로그인 성공 후 리다이렉트: `/ui/list`

### 로그아웃 설정
- 로그아웃 URL: `/logout`
- 로그아웃 후 리다이렉트: `/ui/list`
- 세션 무효화 및 쿠키 삭제

## 데이터베이스 스키마

### Member 테이블
```sql
CREATE TABLE member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    age INT,
    email VARCHAR(255)
);
```

### Role 테이블
```sql
CREATE TABLE role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE
);
```

### Member-Role 연관 테이블
```sql
CREATE TABLE member_roles (
    member_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);
```

## 실행 방법

### 1. 사전 요구사항
- Java 17 이상
- Maven 또는 Gradle
- 데이터베이스 (H2, MySQL, PostgreSQL 등)

### 2. 프로젝트 클론 및 실행
```bash
# 프로젝트 클론 (실제 저장소 URL로 변경)
git clone <repository-url>
cd spring-security-project

# 애플리케이션 실행
./mvnw spring-boot:run
# 또는
./gradlew bootRun
```

### 3. 초기 데이터 설정
애플리케이션 실행 전 Role 테이블에 기본 권한을 추가해야 합니다:
```sql
INSERT INTO role (name) VALUES ('USER');
INSERT INTO role (name) VALUES ('MANAGER');
INSERT INTO role (name) VALUES ('ADMIN');
```

## 사용 방법

### 1. 회원가입
1. 브라우저에서 `http://localhost:8080/ui/list` 접속
2. '회원가입' 버튼 클릭
3. 회원 정보 입력 후 가입 완료

### 2. 로그인
1. 메인 페이지에서 사용자명과 비밀번호 입력
2. '로그인' 버튼 클릭
3. 로그인 성공 시 사용자 정보 및 권한 표시

### 3. 권한별 기능
- **일반 사용자**: 기본 페이지 접근
- **관리자**: 관리자 페이지 접근 가능 (`ROLE_ADMIN` 권한 보유 시)

## 주요 클래스 설명

### SecurityConfig
- Spring Security의 핵심 설정 클래스
- HTTP 보안, 로그인/로그아웃, 권한 설정 관리

### CustomerMember
- Spring Security의 UserDetails 인터페이스를 구현
- Member 엔티티를 Spring Security에서 사용할 수 있도록 래핑

### CustomUserDetailsService
- UserDetailsService 인터페이스 구현
- 사용자명으로 사용자 정보를 로드하는 역할

### MemberService
- 회원가입 시 비밀번호 암호화
- 기본 권한(USER) 자동 할당
- 사용자명으로 회원 조회

## 보안 특징

- **비밀번호 암호화**: BCrypt 해시 함수 사용
- **세션 관리**: Spring Security의 기본 세션 관리 사용
- **CSRF 보호**: Spring Security 기본 CSRF 보호 활성화
- **다중 권한**: 한 사용자가 여러 권한을 가질 수 있는 구조

## 확장 가능한 기능

- JWT 토큰 기반 인증으로 변경
- OAuth2 소셜 로그인 연동
- 권한별 세부 메뉴 관리
- 사용자 프로필 관리 기능
- 비밀번호 찾기/변경 기능

## 실행했을때 스크린샷

### 1. 메인 페이지 (로그인 전)
애플리케이션 실행 후 `http://localhost:8080/ui/list`에 접속하면 보이는 초기 화면입니다.
- 사용자명과 비밀번호 입력 필드
- 로그인 버튼과 회원가입 링크
![스크린샷 2025-06-08 032042.png](src/main/resources/imagefile/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-06-08%20032042.png)
### 2. 회원가입 페이지
`/register` 경로로 접속하여 새로운 회원을 등록할 수 있습니다.
- Username, Password, Name, Age, Email 입력 필드
- 회원가입 완료 후 메인 페이지로 리다이렉트
![스크린샷 2025-06-08 032052.png](src/main/resources/imagefile/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-06-08%20032052.png)
### 3. 로그인 후 메인 페이지
성공적으로 로그인한 후의 화면입니다.
- 로그인한 사용자 정보 표시
- 사용자의 권한(ROLE) 표시
- 로그아웃 버튼
- 권한에 따른 추가 기능 버튼 (관리자의 경우 관리자 페이지 버튼)
![스크린샷 2025-06-08 032022.png](src/main/resources/imagefile/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-06-08%20032022.png)
![스크린샷 2025-06-08 031853.png](src/main/resources/imagefile/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-06-08%20031853.png)
- 
### 4. 데이터베이스 구조
- **member 테이블**: 사용자 기본 정보 저장
- ![스크린샷 2025-06-08 032137.png](src/main/resources/imagefile/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-06-08%20032137.png)
- **role 테이블**: 권한 정보 (USER, MANAGER, ADMIN)
- ![스크린샷 2025-06-08 032223.png](src/main/resources/imagefile/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-06-08%20032223.png)
- **member_roles 테이블**: 사용자와 권한 간의 다대다 관계
- ![스크린샷 2025-06-08 032214.png](src/main/resources/imagefile/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202025-06-08%20032214.png)

## 라이선스

이 프로젝트는 개인학습용으로 만들어졌습니당 FREE!!!

---

**개발자**: Spring Security 학습 프로젝트  
**버전**: 1.0.0  
**최종 업데이트**: 2025년 6월