# sket-io 클래스 설계

#### 게임 규칙
1. 문제 파싱
    - ParsingAnswer_Class
2. 문제 출제자 선택(처음 -> 랜덤, 문제 맞춘 사람이 출제자)
    - SelectExaminer_Class
3. 정답 처리(문제 당: 최대 점수:60점(20초마다 -5))
    - CorrectAnswer_Class
4. 문제 당 제한 시간(문제 당 1분 30초)
    - timeLimit_private Var
5. 문제 전체 못 맞추면 전체 점수 감점(문제 당: 10점)
    - allNotCorrect _final Var
6. 방 하나에 최대 인원: 4명
    - limitPlayer_final Var
7. 등수에 따라 경험치 정산(총 점수 x 1.5)
    - finishGameScore("총 점수")_method
8. 레벨 당 경험치 (1레벨 필요 경험치 = 300, 레벨 당 총 경험치 * 1.5배)
    ```
    // 1레벨 최대 경험치
    int 최대경험치= 300;
    if(최대경험치 <= 처리용 나의 경험치){
        최대경험치 *= 1.5;
        레벨 += 1;
        처리용 나의 경험치 = 0;
    }
    ```


