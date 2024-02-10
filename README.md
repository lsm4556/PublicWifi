# PublicWifi 프로젝트

## 개요
이 프로젝트는 Java EE를 사용하여 공공 와이파이 정보를 관리하는 웹 애플리케이션입니다.

## 프로젝트 구조
- publicwifi
  - config
    - DatabaseConfig.java: 데이터베이스 설정 클래스
  - controller : 컨트롤러 패키지
  - DAO : DB에 접근하는 데이터 액세스 객체 패키지
  - DTO : 데이터 전송 객체 패키지
  - service : 서비스 로직 패키지
  - util
    - ResponseUtil.java: 응답 관련 유틸리티 클래스
    - ValidationUtil.java: 유효성 검사 관련 유틸리티 클래스
- webapp
  - css
  - javascript
  - WEB-INF
    - web.xml: 웹 애플리케이션 설정 파일
  - jsp 파일들
## 사용된 기술
- Java EE
- Servlet
- JSP
- JavaScript
- CSS
- SQLite
## 설치 및 실행
1. 이 저장소를 클론합니다.
2. 프로젝트를 IDE에 import 합니다.
3. 필요한 설정을 수정합니다. (데이터베이스 경로 설정, OPEN API 키 등)
4. Tomcat 서버를 실행하고 프로젝트를 배포합니다.
5. 웹 브라우저에서 http://localhost:8080/publicwifi 로 접속합니다.
