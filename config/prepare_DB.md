## DB 설정 방법

0. [MySQL 설치](https://dev.mysql.com/downloads/file/?id=473605) || 웹서버 MySQL
1. SQL을 실행할 콘솔을 준비한다.
1.1 IntelliJ에서 실행
`Ctrl` + `Tab` > Database > + 아이콘 > Data Source > MySQL  
Username, Password 입력(포트번호 주의) > 콘솔 실행
1.2 MySQL Workbench
1.3 등등 콘솔 사용

2. create_table.sql 실행
  데이터베이스와 테이블을 생성하는 SQL 실행 (`Alt`+`Enter`로 편리하게 실!행!)

3. (선택) insert_dump_data.sql 실행
테스트 데이터 추가