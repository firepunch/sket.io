import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import $ from 'jquery';
import { modal } from 'react-redux-modal';

const propTypes = {
    canvas: ReactPropTypes.object
};

const defaultProps = {
    canvas: {}
};

// 모달이 두 번 호출되는 문제 해결 - 해결
// 퀴즈 하나가 끝나고 다음으로 넘어가는 처리 구현 - 일부 해결

// 문제 출제 및 처리를 모두 middleware 에서 처리하기 때문에 UI에 어떻게 반영해야할지 모르겠음 - 해결
// 방 생성 시 조건
// 비밀번호 입력


//Canvas
var canvas;
var context;

//Variables
var canvasx;
var canvasy;

var last_mousex = 0;
var last_mousey = 0;

var mousex = 0;
var mousey = 0;
var mousedown = false;

var chatList = [];


class GameArea extends Component {
    constructor(props) {
        super(props);

        this.state = {
            chat: '',
            time: this.props.roomInfo.timeLimit
        }
    }
    componentDidMount() {

        //Variables
        canvas = document.getElementById('canvas');
        context = canvas.getContext('2d');

        let timer = setInterval(() => {
            if (this.state.time > 0 && this.props.isQuiz && this.props.isTimer) {
                // 퀴즈가 진행중일 때
                this.setState({
                    ...this.state,
                    time: this.state.time - 1
                })

                if (this.state.time <= 0) {
                    // timeout으로 게임이 종료되었을 때
                    if (this.props.userId === this.props.examinerId) {
                        // 출제자의 시간을 기준으로 하나의 요청만 보냄
                        this.props.handleTimeout(this.props.roomId);
                    }
                    // context.clearRect(0, 0, canvas.width, canvas.height);
                    // clearInterval(timer);   // 시간이 끝나면 setInterval 종료
                }
            }
        }, 1000);
    }

    // 컴포넌트의 props가 update 되기 전에 실행됨
    // 여기서 값을 update 할지 안할지 정하는 것임
    // 다음 props는 함수의 인자로 받아올 수 있음
    componentWillReceiveProps(nextProps) {

        // CHATTING
        if (chatList[chatList.length - 1] !== nextProps.chat) {
            chatList.push(nextProps.chat)
        }

        // QUIZ RESULT MODAL
        // 순서가 엉켜서 답이 제대로 오지 않음
        // 중간에 다른 내용이 있어야 함
        if (!nextProps.isQuiz && nextProps.chat.correct
                && this.props.modals.length < 2 && this.props.isQuizResultModal) {
            this.addQuizResultModal('정답', nextProps.chat.nick, nextProps.chat.msg, nextProps.chat.score, false);
        }

        // QUIZ MODAL
        if (nextProps.isQuiz && this.props.isQuizModal && nextProps.modals.length < 2) {
            if (this.props.userId === this.props.examinerId
                && typeof nextProps.quiz.quiz !== 'undefined') {
                // 문제 출제자일 때 보여지는 모달
                // this.props.handleQuizModal();
                console.log("examiner modal")
                this.addQuizModal(nextProps.quiz.quiz);
            } else if (this.props.userId !== this.props.examinerId) {
                // 문제 출제자가 아닐 때
                // this.props.handleQuizModal();
                console.log("responser modal")
                this.addQuizModal('문제를 출제 중입니다...');
            }
        }

        // TIMEOUT
        if (nextProps.isTimeoutModal && nextProps.modals.length < 2) {
            // timeout 메시지를 받았을 때 모달을 띄워줌
            let timeoutScore = this.props.roomInfo.playerList[0].score
                                - nextProps.roomInfo.playerList[0].score
            this.addQuizResultModal('시간 종료', '-' + timeoutScore, '', '', true);
            this.props.handleTimeoutModal();
        }

        // ROUND CHANGE
        if (this.props.roundInfo !== nextProps.roundInfo) {
            // 라운드가 바뀌었을 때의 처리
            this.setState({
                ...this.state,
                time: this.props.roomInfo.timeLimit
            })
            if (nextProps.roundInfo > 1) {
                console.log('라운드가 바뀜');
                // this.props.handleQuizModal(true);
            }
            context.clearRect(0, 0, canvas.width, canvas.height);
        }



        canvasx = $("#canvas").offset().left;   // 캔버스의 x 좌표값
        canvasy = $("#canvas").offset().top;    // 캔버스의 y 좌표값

        // 좌표를 이용하여 그림을 그림
        context.beginPath();

        context.globalCompositeOperation = 'source-over';
        context.strokeStyle = 'black';
        context.lineWidth = 3;

        if (nextProps.canvas.mouse === 'up') {
            // 점 하나만 찍고 땔 때
            context.moveTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
            context.lineTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
        } else if (nextProps.canvas.mouse === 'move') {
            // 마우스를 누른 상태에서 움직일 때

            if (this.props.canvas.mouse === 'up') {
                // 이전에 마우스를 땠다가 새로 그릴 때
                context.moveTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
                context.lineTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
            } else {
                context.moveTo(this.props.canvas.clickX, this.props.canvas.clickY);
                context.lineTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
            }
        }

        context.lineJoin = context.lineCap = 'round';
        context.stroke();
    }

    render() {

        let canvas = (  // 문제 출제자가 아니면 캔버스에 핸들링 함수를 포함하지 않음
            <canvas id="canvas" width="600" height="600">
                이 브라우저는 canvas를 지원하지 않는 브라우저입니다. 포기하시고 크롬을 사용하십시오.
            </canvas>
        )

        const chatItem = chatList.map((data, index) => {
            return (
                <div className="chat-item">
                    <span>{ data.nick }</span>
                    <span>{ data.msg }</span>
                    <span>{ data.time }</span>
                </div>
            )
        });

        let chat = (
            <div className="sket-chatting">
                <div className="chat-body overflow-scroll">
                    { chatItem }
                </div>
                <div className="chat">
                    <input type="text" id="chat-talk"
                            onChange={ (evt) => this.handleChatChange(evt) }
                            onKeyPress={ (evt) => this.handleChatKeyPress(evt) }/>
                    <button id="chat-btn"
                            onClick={ () => this.handleChatting() }>
                        <p>전송</p>
                    </button>
                </div>
            </div>
        )


        if (this.props.userId === this.props.examinerId) {
            canvas = (
                <canvas id="canvas" width="600" height="600"
                    onMouseDown={ (evt) => this.handleMouseDown(evt) }
                    onMouseUp={ (evt) => this.handleMouseUp(evt) }
                    onMouseMove={ (evt) => this.handleMouseMove(evt) }
                    onMouseLeave={ (evt) => this.handleMouseMove(evt) } >
                    이 브라우저는 canvas를 지원하지 않는 브라우저입니다. 포기하시고 크롬을 사용하십시오.
                </canvas>
            )

            chat = (
                <div className="sket-chatting">
                    <div className="chat-body overflow-scroll">
                        { chatItem }
                    </div>
                    <div className="chat">
                        <input type="text" id="chat-talk" disabled />
                        <button id="chat-btn">
                            <p>전송</p>
                        </button>
                    </div>
                </div>
            )
        }


        return(
            <div className="game-area">
                <div className="sket-round-info">
                    <h3>Round { this.props.roundInfo + '/' + this.props.roomInfo.numRound }</h3>
                </div>

                <div className="sketch-area">

                    { canvas }

                    <div className="progress progress-bar-vertical">
                        <div className="progress-bar progress-bar-danger progress-bar-striped active"
                            role="progressbar" aria-valuenow="100"
                            aria-valuemin="0" aria-valuemax="100"
                            style={{
                                'height': (parseInt(this.state.time / this.props.roomInfo.timeLimit * 100) + '%')
                            }}>
                            <span className="sr-only">&nbsp;</span>
                        </div>
                    </div>
                </div>

                { chat }
            </div>
        );
    }

    handleMouseDown(e) {
        // clientX, clientY : element를 클릭했을 때의 좌표 값
        last_mousex = mousex = parseInt(e.clientX - canvasx);
        last_mousey = mousey = parseInt(e.clientY - canvasy);

        mousedown = true;
    }

    handleMouseUp(e) {
        if (mousedown) {
            let msg = {
                userId: this.props.userId,
                roomId: this.props.roomId,
                clickX : last_mousex,
                clickY : last_mousey,
                mouse: 'up'
            };

            this.props.handleCanvasData(msg);
        }

        mousedown = false;
    }

    handleMouseMove(e) {
        mousex = parseInt(e.pageX - canvasx);
        mousey = parseInt(e.pageY - canvasy);

        if (mousedown) {
            last_mousex = mousex;
            last_mousey = mousey;

            let msg = {
                userId: this.props.userId,
                roomId: this.props.roomId,
                clickX : last_mousex,
                clickY : last_mousey,
                mouse: 'move'
            };

            this.props.handleCanvasData(msg);
        }            handleTimeout: this.props.handleTimeout

    }

    handleChatChange(evt) {
        this.setState({
            ...this.state,
            chat: evt.target.value
        })
    }

    handleChatKeyPress(evt) {
        let key = evt.keyCode || evt.which;

        if (key === 13) {
            this.handleChatting();
        }
    }

    handleChatting() {
        this.props.handleChatData(
            this.props.roomId,
            this.props.userId,
            this.state.time,
            this.state.chat
        );

        document.getElementById('chat-talk').value = '';
    }

    // <div className="sketch-btn">
    //     <img src={"img/pencil-icon.png"} onClick={ () => this.useTool('draw') } />
    //     <img src={"img/eraser-icon.png"} onClick={ () => this.useTool('erase') } />
    // </div>

    addQuizModal(quiz) {
        modal.add(QuizModal, {
            title: '문제',
            size: 'large', // large, medium or small,
            closeOnOutsideClick: false, // (optional) Switch to true if you want to close the modal by clicking outside of it,
            hideTitleBar: false, // (optional) Switch to true if do not want the default title bar and close button,
            hideCloseButton: true, // (optional) if you don't wanna show the top right close button
            quiz: quiz,
            roomId: this.props.roomId,
            userId: this.props.userId,
            examinerId: this.props.examinerId,

            handlequizStart: this.props.handlequizStart,
            handleQuizModal: this.props.handleQuizModal,
            handleQuizResultModal: this.props.handleQuizResultModal,
            startTimer: this.props.handleStartTimer
            //.. all what you put in here you will get access in the modal props ;)
        });
    }

    addQuizResultModal(title, nick, answer, score, timeout) {
        modal.add(QuizResultModal, {
            title: title,
            size: 'large',
            closeOnOutsideClick: false,
            hideTitleBar: false,
            hideCloseButton: true,

            nick: nick,
            answer: answer,
            score: score,
            isTimeout: timeout,

            handleQuizModal: this.props.handleQuizModal,
            handleQuizResultModal: this.props.handleQuizResultModal,
            limitTime: this.props.roomInfo.timeLimit
        });
    }
}

class QuizModal extends Component {

    componentDidMount() {
        console.log('QUIZ MODAL');
        this.props.handleQuizModal(false);
        // this.props.handleQuizResultModal(true);
        setTimeout(() => {
            this.props.removeModal();
            this.props.startTimer();
            if (this.props.userId === this.props.examinerId) {
                // 문제 출제자일 때만 함수 호출
                console.log('handlequizStart')
                // this.props.handlequizStart(this.props.roomId);
            }
        }, 3000);
    }

    render() {
        return (
            <div className="show-quiz">
                <p>{ this.props.quiz }</p>
            </div>
        );
    }
}

class QuizResultModal extends Component {
    componentDidMount() {
        console.log('QUIZ RESULT MODAL');
        this.props.handleQuizResultModal(false);
        setTimeout(() => {
            this.props.handleQuizModal(true);
            this.props.removeModal();
        }, 3000);
    }

    render() {
        let result = (
            <div className="show-quiz quiz-result">
                <p>{ this.props.nick }</p>&nbsp;
                <p>{ '답: ' + this.props.answer }</p>&nbsp;
                <p>{ '점수: ' + this.props.score }</p>
            </div>
        )

        let timeout = (
            <div className="show-quiz quiz-result">
                <p>시간 종료</p>
                <p>{ this.props.nick }</p>&nbsp;
            </div>
        )

        return (
            <div className="show-quiz quiz-result">
                { this.props.isTimeout ? timeout : result }
            </div>
        )
    }

    componentDidUpdate() {
        // this.props.handleQuizResultModal(true);
    }
}

GameArea.propTypes = propTypes;
GameArea.defaultProps = defaultProps;

export default GameArea;
