# 온라인 도서 관리 시스템 (RESTful API)

## 1. 프로젝트 개요
- 도서 관리 (등록, 조회, 수정, 삭제)
- 사용자 관리 (등록, 조회)
- 대출 관리 (대출, 상태, 반납)

## 2. 기술 스택
- Backend: Java 17, Spring Boot 3.4
- Database: MySQL
- Security: Spring Security, JWT
- API Documentation: Swagger (Springdoc OpenAPI)
- Build Tool: Gradle

## 3. 프로젝트 실행 방법
- API 문서(Swagger) 접속
  - http://localhost:8080/swagger-ui/index.html
- API 사용을 위해 사용자는 먼저 사용자 등록 후 로그인을 해야 합니다.
- 로그인 후 반환된 토큰을 Authorization 헤더에 Bearer token 형식으로 추가하여 요청해야 합니다.