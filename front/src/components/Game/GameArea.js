import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import $ from 'jquery';
import { modal } from 'react-redux-modal';

const propTypes = {
};

const defaultProps = {
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

        canvasx = $("#canvas").offset().left;
        canvasy = $("#canvas").offset().top;

        setInterval(() => {
            if (this.props.isQuiz)  {   // 퀴즈가 진행중일 때
                this.setState({
                    ...this.state,
                    time: this.state.time - 1
                })
            }
        }, 1000);
    }

    // 컴포넌트의 props가 update 되기 전에 실행됨
    // 여기서 값을 update 할지 안할지 정하는 것임
    // 다음 props는 함수의 인자로 받아올 수 있음
    componentWillReceiveProps(nextProps) {

        // if (this.props.chat.correct === true) {
        //
        // }
        if (chatList[chatList.length - 1] !== this.props.chat) {
            chatList.push(this.props.chat)
        }

        // 문제 출제자일 때 보여지는 모달
        if (this.state.isModal) {
            if (this.props.userId === this.props.examinerId
                && nextProps.quiz.quiz !== '') {
                    this.addModal(nextProps.quiz.quiz);
                    this.setState({
                        ...this.state,
                        isModal: false
                    })
                }
            }
            // else {
            //     this.addModal('문제를 출제 중입니다...');
            //
            //     this.setState({
            //         ...this.state,
            //         isModal: false
            //     })
            // }
        }

        // 문제 출제자가 아닐 때 보여지는 모달
        if (nextProps.quiz.isQuiz && this.props.isModal) {
            if (this.props.userId !== this.props.examinerId) {
                this.addModal('문제를 출제 중입니다...');
                this.setState({
                    ...this.state,
                    isModal: false
                })
            }
        }

        // 좌표를 이용하여 그림을 그림
        ctx.beginPath();

        ctx.globalCompositeOperation = 'source-over';
        ctx.strokeStyle = 'black';
        ctx.lineWidth = 3;

        ctx.moveTo(nextProps.canvas.clickX, nextProps.canvas.clickY);
        ctx.lineTo(mousex, mousey);
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

        const chat = chatList.map((data, index) => {
            return (
                <div className="chat-item">
                    <span>{ data.nick }</span>
                    <span>{ data.msg }</span>
                    <span>{ data.time }</span>
                </div>
            )
        });

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
        }


        return(
            <div className="game-area">
                <div className="sket-round-info">
                    <h3>Round { this.props.roundInfo.round + '/' + this.props.roomInfo.numRound }</h3>
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

                <div className="sket-chatting">
                    <div className="chat-body overflow-scroll">
                        { chat }
                    </div>
                    <div className="chat">
                        <input type="text" id="chat-talk"
                                onChange={ (evt) => this.handleChatChange(evt) }/>
                        <button id="chat-btn"
                                onClick={ () => this.handleChatting() }>
                            <p>전송</p>
                        </button>
                    </div>
                </div>
            </div>
        );
    }

    handleMouseDown(e) {
        last_mousex = mousex = parseInt(e.clientX - canvasx);
        last_mousey = mousey = parseInt(e.clientY - canvasy);

        mousedown = true;
    }

    handleMouseUp(e) {
        mousedown = false;
    }

    handleMouseMove(e) {
        mousex = parseInt(e.clientX-canvasx);
        mousey = parseInt(e.clientY-canvasy);

        if( mousedown ) {
            last_mousex = mousex;
            last_mousey = mousey;

            let msg = {
                userId: this.props.userId,
                roomId: this.props.roomId,
                clickX : last_mousex,
                clickY : last_mousey
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

    handleChatting() {
        console.log('time : ' + this.state.time);
        console.log('chat : ' + this.state.chat);

        this.props.handleChatData(
            this.props.roomId,
            this.props.userId,
            this.state.time,
            this.state.chat
        )
    }

    // <div className="sketch-btn">
    //     <img src={"img/pencil-icon.png"} onClick={ () => this.useTool('draw') } />
    //     <img src={"img/eraser-icon.png"} onClick={ () => this.useTool('erase') } />
    // </div>

    addModal(quiz) {
        modal.add(modalComponent, {
            title: '문제',
            size: 'large', // large, medium or small,
            closeOnOutsideClick: true, // (optional) Switch to true if you want to close the modal by clicking outside of it,
            hideTitleBar: false, // (optional) Switch to true if do not want the default title bar and close button,
            hideCloseButton: true, // (optional) if you don't wanna show the top right close button
            quiz: quiz,
            handlequizStart: this.props.handlequizStart,
            roomId: this.props.roomId
            //.. all what you put in here you will get access in the modal props ;)
        });
    }
}

class modalComponent extends Component {

    componentDidMount() {
        this.props.handlequizStart(this.props.roomId);
        setTimeout(() => {
            this.removeThisModal();
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
