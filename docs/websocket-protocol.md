<b>모든 key-value는 쌍따옴표(")로 쌓여있다.</b><br>
<b>최상위 key는 type과 data뿐이다.</b> type을 제외한 정보는 data안에 위치한다.<br>

예 ) "type" : "GOOGLE"

<table>
    <tbody>
    <tr>
        <th></th>
        <th align=left>Client->Server</th>
        <th align=left>Server->Client</th>
    </tr>
    <tr>
        <td>구글 로그인</td>
        <td align="center">-</td>
        <td>
            type : GOOGLE,<br>
            id: (유저 아이디),<br>
            name : (유저 이름),<br>
            nick : (nickXX or 유저 닉넴),<br>
            picture : (유저 사진 링크),<br>
            level : (유저 레벨),<br>
            limitExp: (레벨에 필요한 경험치),<br>
            totalExp : (유저 총경험치),<br>
            curExp : (유저 현재경험치),<br>
        </td>
    </tr>
    <tr>
        <td>페북 로그인</td>
        <td align="center">-</td>
        <td>
            type : FACEBOOK,<br>
            id: (유저 아이디),<br>
            name : (유저 이름),<br>
            nick : (nickXX or 유저 닉넴),<br>
            picture : (유저 사진 링크),<br>
            level : (유저 레벨),<br>
            limitExp: (레벨에 필요한 경험치),<br>
            totalexp : (유저 총경험치),<br>
            curexp : (유저 현재경험치)<br>
        </td>
    </tr>
    <tr>
        <td>게스트 로그인</td>
        <td align="center">-</td>
        <td>
            type : GUEST,<br>
            id: (유저 아이디),<br>
            nick : (nickXX or 유저 닉넴),<br>
            level : 1,<br>
            limitExp: (레벨에 필요한 경험치),<br>
            totalExp : 0,<br>
            curExp : 0<br>
        </td>
    </tr>
    <tr>
        <td>유저 리스트</td>
        <td align="center">-</td>
        <td>
            type : USER_LIST,<br>
            userList : [{<br>
            name: (유저 이름),<br>
            level: (레벨)<br>
            },{<br>
            name: (유저 이름),<br>
            level: (레벨)<br>
            }]
        </td>
    </tr>
    <tr>
               
        <td>방 리스트</td>
               
        <td align="center">-</td>
        <td>
            type : ROOM_LIST,<br>
            roomList : [{<br>
            roomId: 방 아이디, <br>
            roomName: 방 이름, <br>
            round: 현재 라운드, <br>
            roundLimit: 제한 라운드, <br>
            timeLimit: 제한 시간, <br>
            userNumLimit: 최대 인원, <br>
            userNum: 현재 인원, <br>
            isLocked: 비밀방 여부, <br>
            password: 암호
            }...]
        </td>
    </tr>
    <tr>
               
        <td>방 정보</td>
               
        <td>-</td>
        <td>
            type : ROOM_INFO,<br>
            roomList : [{<br>
            nick: 닉네임, <br>
            picture: 사진, <br>
            level: 레벨, <br>
            isReady: 준비 여부, <br>
            id: 아이디, <br>
            master: 방장 <br>
	    score : 점수
            }...]
        </td>
    </tr>
    <tr>
    <td>방 생성</td>
    <td>
        type : CREATE_ROOM,<br>
        roomName : (방 이름),<br>
        lock : (true or false),<br>
        password : (null or 1234),<br>
        master : (유저 아이디), <br>
        userNumLimit : (최대 입장 가능 유저 수), <br>
        limitRound : 라운드 수,<br>
        limitTime : 제한시간
    </td>
    <td>
        type : CREATE_ROOM,<br>
        roomId : (룸 아이디)
    </td>
    </tr>
    <tr>
        <td>방 입장</td>
        <td> type : ENTER_ROOM,<br>
            roomId : (방 아이디),<br>
            userId : (user id)
        </td>
        <td>type : ENTER_ROOM,<br>
            roomId : (방 아이디),<br>
            readyUser : [{<br>
            &nbsp;&nbsp;id : (유저 아이디),<br>
            &nbsp;&nbsp;isReady : (boolean)
            <br>&nbsp;}{<br>
            &nbsp;&nbsp;id : (유저 아이디),<br>
            &nbsp;&nbsp;isReady : (boolean)
            <br>}]
        </td>
    </tr>
    <tr>
        <td> 게임 준비</td>
        <td> type : GAME_READY,<br>
            id : 유저 아이디,<br>
            isReady : (true or false)
        </td>
        <td> type : PLAYER_GAME_READY,<br>
            id : 유저 아이디,<br>
            isReady : (true or false)
        </td>
    </tr>
    <tr>
        <td>방 퇴장</td>
        <td> type : EXIT_ROOM,<br>
            userId : (유저 아이디),<br>
            roomId : (방 아이디)
        </td>
        <td> type : EXIT_ROOM,<br>
            userId : (유저 아이디),<br>
            roomId : (방 아이디)
        </td>
    </tr>
    <tr>
        <td>게임 종료</td>
        <td> type : GAME_END,<br>
            roomId : (방 아이디),<br>
            scoreInfo : [{<br>
            &nbsp;&nbsp;id : (유저 아이디),<br>
            &nbsp;&nbsp;level : (유저의 레벨),<br>
            &nbsp;&nbsp;score : (해당 유저의 점수)
            <br>&nbsp;},{<br>
            &nbsp;&nbsp;id : (유저 아이디),<br>
            &nbsp;&nbsp;level : (유저의 레벨),<br>
            &nbsp;&nbsp;score : (해당 유저의 점수)
            <br>},...]
        </td>
        <td> type : GAME_END,<br>
            expinfo : [{<br>
            &nbsp;&nbsp;id : (유저 아이디),<br>
            &nbsp;&nbsp;level : (유저의 레벨),<br>
            &nbsp;&nbsp;exp : (해당 유저의 경험치)
            <br>&nbsp;},{<br>
            &nbsp;&nbsp;id : (유저 아이디),<br>
            &nbsp;&nbsp;level : (유저의 레벨),<br>
            &nbsp;&nbsp;exp : (해당 유저의 경험치)
            <br>},...]
        </td>
    </tr>
        <tr>
        <td>문제 보냄 알람</td>
        <td>type: "START_QUIZ",<br>
            roomId: roomId
        </td>
        <td>type: "START_QUIZ",<br>
            round : 현재 라운드 수,<br>
            // 모든 라운드를 진행해 게임이 끝났을 경우 true 정보만 전송<br>
            gameEnd : true or false
        </td>
        </tr>
    <tr>
        <td>랜덤 문제 전송</td>
        <td> type : RANDOM_QUIZ,<br>
            roomId : 룸 아이디,<br>
            userId : 문제 받을 사람 아이디
        </td>
        <td> type : RANDOM_QUIZ,<br>
            quiz : 출제된 문제,<br>
            userId : 문제 받을 사람 아이디,
            roomId
        </td>
    </tr>
    <tr>
        <td>랜덤 출제자 전송</td>
        <td></td>
        <td>type : RANDOM_EXAMINER,<br>
            id : (유저 아이디),<br>
            roomId : 2
        </td>
    </tr>
    <tr>
        <td>캔버스 그리기</td>
        <td>
            type : "CANVAS_DATA",<br>
            userId: (출제자 아이디),<br>
            roomId: ( 방 아이디 ),<br>
            clickX : clickX,<br>
            clickY : clickY
        </td>
        <td>
            type : "CANVAS_DATA",<br>
            clickX : clickX,<br>
            clickY : clickY
            // 위의 정보를 이용해 코드로 그림을 그려줌
        </td>
    </tr>
    <tr>
        <td>채팅&정답처리</td>
        <td> type : CHAT_DATA,<br>
            userId : 발신자 아이디,<br>
            roomId : 룸 아이디,<br>
            restTime : 남은 시간,<br>
            msg : 채팅 내용
        </td>
        <td>
            type : CHAT_DATA,<br>
            userId : 발신자 아이디,<br>
            userNick : 발신자 닉네임,<br>
            roomId : 룸 아이디,<br>
            msg : 채팅 내용,<br>
            time : 수신 시각,<br>
            correct : true/false,<br>
            // 정답이라면 아래의 key-value 추가로 전송<br>
            score : 시간에 따른 정답자의 점수
        </td>
    </tr>
    <tr>
        <td>전체 감점</td>
        <td>
            type : GAME_TIMEOUT,<br>
            roomId : 룸 아이디
        </td>
        <td>
            type : GAME_TIMEOUT,<br>
            roomId : 룸 아이디,<br>
            score : 10
        </td>
    </tr>
    <tr>
        <td>랭킹 보여주기</td>
        <td> type : SHOW_RANK,<br>
            id : (유저 아이디)
        </td>
        <td> type : SHOW_RANK,<br>
            myInfo : [{<br>
            &nbsp;&nbsp;nick : (자신의 닉네임),<br>
            &nbsp;&nbsp;level : (자신의 레벨),<br>
            &nbsp;&nbsp;rank : (자신의 랭킹)
            <br>}], otherInfo : [{<br>
            &nbsp;&nbsp;nick : (유저의 닉네임),<br>
            &nbsp;&nbsp;level : (유저의 레벨),<br>
            &nbsp;&nbsp;exp : (유저의 랭킹)
            <br>&nbsp;}, ...]
        </td>
    </tr>
    <tr>
	<td>출제자 변경</td>
	<td></td>
	<td>
		type : SET_EXAMINER,<br>
		id : (유저 아이디)
	</td>

</tr>
    </tbody>
</table>
