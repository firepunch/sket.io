# sket-io 클래스 설계

#### controllers Package - Class
1. LoginController
2. PlayerController
3. RoomController
4. QuizController
5. SystemController
6. RankingController
7. GameController
8. SignUpController
<br>

#### model.data Package - Class
1. UserData : 유저 데이터에 대한 getter, setter
	- getUserId() : 유저 Id 를 반환
	- getUserNick() : 유저 닉네임 반환
	- getUserPw() : 유저 패스워드 반환
	- setUserId(String id) : 유저 Id 설정
2. PlayerData : 게임에 참가하는 유저에 대한 getter, setter
	- getScore() : 현재 유저 점수를 반환
	- setScore(int score) : 유저 점수를 설정
	- getNick() : 유저의 닉네임 반환
	- getId() : 유저의 Id 반환
 	- getTotalExp() : 유저의 현재 레벨까지의 총 경험치 반환
	- setTotalExp() : 유저의 현재 레벨까지의 총 경험치 설정
	- getCurretExp() : 유저의 경험치 현재 레벨의 경험치 반환(0 ~ 100%)
	- setCurrentExp(int exp) : 유저의 경험치 반환
	- getLevel() : 유저의 현재 레벨 반환
	- setLevel() : 유저의 현재 레벨 설정
<br>

#### model.action Package - Class
1. SignUpAction : 회원가입 액션
	- loginFacebook() : 페이스북으로 로그인 할 시
	- loginGoogle() : 구글로 로그인 할 시
	- loginSocial() : 페이스북, 구글로 로그인 할 시에 호출하며 이 메소드 내부에서 loginFacebook(), loginGoogle()이 호출된다.
	- insertDB(UserData data) : 회원가입 한 유저의 정보를 DB에 저장
	- loginGuest() : 게스트가 로그인 했을 시에
2. PlayerAction : 게임 중인 플레이어 대한 액션
	- updateNick(UserData data) : 유저의 닉네임 변경 
	- plusTotalExp(UserData data) : 유저의 현재 레벨까지의 경험치 덧셈
3. QuizAction : 퀴즈에 대한 액션
	- parseQuiz(String textFile) : textFile을 이용하여 파싱
	- insertQuiz() : DB에 파싱한 데이터 저장
	- selectQuiz() : DB에서 단어 빼오기
4. GameAction : 게임 중에 대한 액션
	- sendAnswer() : 클라이언트에게 서버 정답을 전달
 	- sendPicture() : 그리고 있는 그림 클라이언트에게 전달
5. SystemAction : 게임이 끝난 뒤에 대한 액션
	- divideExp() : 게임이 끝나고 참가자들에게 점수의 1.5배를 곱해 경험치를 분배한다.
	- calPlayerLevel(RoomAction room) : 참가자들의 경험치를 계한해 레벨업 처리를 해준다.
6. RoomAction : 방의 정보에 대한 액션
	- createRoomId() : 방에 대한 고유 아이디 생성하는 생성자
	- enterPlayer(String id) : 방에 사용자가 들어왔다.
	- gameStart() : 게임 스타트 이벤트가 발생할 때 호출하는 메소드 
	- getRoomPw() : 방에 대한 비밀번호에 대해 반환
	- getRoomId() : 방에 대한 고유 아이디 반환
	- getPlayerId() : 방 안의 유저들의 아이디를 반환
	- exitPlayer() : 방에서 유저들 나갔을 때
	- checkPw(String pw) : 사용자가 비밀 방에 들어가려고 했을 때 입력한 비밀번호와 해당 방의 비밀번호가 일치하는지 검사
