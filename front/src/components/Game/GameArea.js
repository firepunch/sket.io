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

//Canvas
var canvas;
var ctx;

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
            time: this.props.roomInfo.timeLimit,
            isModal: true
        }
    }
    componentDidMount() {

        //Variables
        canvas = document.getElementById('canvas');
        ctx = canvas.getContext('2d');
    }

    // 컴포넌트의 props가 update 되기 전에 실행됨
    // 여기서 값을 update 할지 안할지 정하는 것임
    // 다음 props는 함수의 인자로 받아올 수 있음
    componentWillReceiveProps(nextProps) {

        /*
        if (nextProps.chat.correct) {
            if (nextProps.chat.userId === this.props.userId) {
                // 현재 사용자가 정답을 맞췄다면

            } else {
                // 다른 사람이 정답을 맞춤

            }
        }
        */

        if (nextProps.isQuiz && nextProps.isTimer) {
            // 일단 이 부분의 구현은 나중으로 미루자.(시작 시점을 언제로 잡아야할지 잘 모르겠음)
            setInterval(() => {
                if (this.state.time > 0) {
                    // 퀴즈가 진행중일 때
                    this.setState({
                        ...this.state,
                        time: this.state.time - 1
                    })
                }
            }, 1000);
        }

        if (chatList[chatList.length - 1] !== nextProps.chat) {
            chatList.push(nextProps.chat)
        }

        // 문제 출제자일 때 보여지는 모달
        if (nextProps.isQuiz && this.state.isModal) {
            if (this.props.userId === this.props.examinerId
                && typeof nextProps.quiz.quiz !== 'undefined') {
                    this.addModal(nextProps.quiz.quiz);
                    this.setState({
                        ...this.state,
                        isModal: false
                    })
            }
            if (this.props.userId !== this.props.examinerId) {
                this.addModal('문제를 출제 중입니다...');
                this.setState({
                    ...this.state,
                    isModal: false
                })
            }
        }

        // 문제 출제자가 아닐 때 보여지는 모달
        // if (nextProps.isQuiz && this.state.isModal) {
        //
        // }


        canvasx = $("#canvas").offset().left;   // 캔버스의 x 좌표값
        canvasy = $("#canvas").offset().top;    // 캔버스의 y 좌표값


        // 좌표를 이용하여 그림을 그림
        ctx.beginPath();

        ctx.globalCompositeOperation = 'source-over';
        ctx.strokeStyle = 'black';
        ctx.lineWidth = 3;

        if (nextProps.canvas.mouse === 'up') {
            // 점 하나만 찍고 땔 때
            ctx.moveTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
            ctx.lineTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
        } else if (nextProps.canvas.mouse === 'move') {
            // 마우스를 누른 상태에서 움직일 때

            if (this.props.canvas.mouse === 'up') {
                // 이전에 마우스를 땠다가 새로 그릴 때
                ctx.moveTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
                ctx.lineTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
            } else {
                ctx.moveTo(this.props.canvas.clickX, this.props.canvas.clickY);
                ctx.lineTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
            }
        }

        ctx.lineJoin = ctx.lineCap = 'round';
        ctx.stroke();
    }

    render() {

        if (this.state.time <= 0) {
            // this.props.handleTimeout();
            console.log('timeout');
        }

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
                                'height': (this.state.time / this.props.roomInfo.timeLimit) * 100}
                            }>
                            <span className="sr-only">&nbsp;</span>
                        </div>
                    </div>
                </div>

                { chat }
            </div>
        );
    }

    startTimer() {
        if (this.props.isQuiz) {
            setInterval(() => {
                if (this.state.time > 0) {
                    // 퀴즈가 진행중일 때
                    this.setState({
                        ...this.state,
                        time: this.state.time - 1
                    })
                }
            }, 1000);
        }
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
        }
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

    addModal(quiz) {
        modal.add(modalComponent, {
            title: '문제',
            size: 'large', // large, medium or small,
            closeOnOutsideClick: false, // (optional) Switch to true if you want to close the modal by clicking outside of it,
            hideTitleBar: false, // (optional) Switch to true if do not want the default title bar and close button,
            hideCloseButton: true, // (optional) if you don't wanna show the top right close button
            quiz: quiz,
            roomId: this.props.roomId,
            userId: this.props.userId,
            examinerId: this.props.examinerId,
            startTimer: this.props.handleStartTimer
            // handlequizStart: this.props.handlequizStart,
            //.. all what you put in here you will get access in the modal props ;)
        });
    }
}

class modalComponent extends Component {

    componentDidMount() {
        setTimeout(() => {
            // if (this.props.userId === this.props.examinerId) {
            //     this.props.handlequizStart(this.props.roomId);
            // }
            this.removeThisModal();
            this.props.startTimer();
        }, 3000);
    }

    removeThisModal() {
        this.props.removeModal();
    }

    render() {
        return (
            <div className="show-quiz">
                <p>{ this.props.quiz }</p>
            </div>
        );
    }
}

GameArea.propTypes = propTypes;
GameArea.defaultProps = defaultProps;

export default GameArea;
