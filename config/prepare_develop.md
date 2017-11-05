## 개발 준비
### Backend
Intellij 에서 'Import Project'한 후의 단계이다.

1. Tomcat 연동  
[Sket.io Wiki](https://github.com/firepunch/sket.io/wiki)에 Tomcat 8.5.13버전을 사용했다고 나와있다. 지금(17.11.5)은 버전 9까지 나왔지만 사이드이펙트가 발생할 가능서잉 있기때문에 개발된 버전과 가장 비슷한 [8.5.23버전을](https://tomcat.apache.org/download-80.cgi#8.5.23) 사용했다.   
1.1 Run > Edit Configurations 를 통해 Run/Debug Configurations 다이알로그를 연다.  
1.2 플러스 버튼 또는 `Alt` + `Insert` 버튼을 클릭해 톰캣 Local 서버를 추가한다.  
1.3 Application Server에 설치된 톰캣경로를 지정한다.    

/*
2. 웹서버 구축
필자는 [비트나미](https://bitnami.com/stack/wamp/installer)를 사용했다. 
*/

3. JDK 연동

4. `prepare_DB` 파일을 보며 DB를 세팅한다.
5. Configure 파일을 생성하여 DB에 접근가능하게 한다.  
페북, 구글 로그인 할 떄 피룡한 시크릿 코드도 여기있슴당

### Frontend

