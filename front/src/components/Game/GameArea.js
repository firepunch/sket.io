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


class GameArea extends Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
        //Variables
        canvas = document.getElementById('canvas');
        ctx = canvas.getContext('2d');

        canvasx = $("#canvas").offset().left;
        canvasy = $("#canvas").offset().top;
    }

    componentWillReceiveProps() {
        // 좌표를 이용하여 그림을 그림
        ctx.beginPath();

        ctx.globalCompositeOperation = 'source-over';
        ctx.strokeStyle = 'black';
        ctx.lineWidth = 3;

        ctx.moveTo(this.props.canvas.clickX, this.props.canvas.clickY);
        ctx.lineTo(mousex, mousey);
        ctx.lineJoin = ctx.lineCap = 'round';
        ctx.stroke();
    }

    handleMouseDown(e) {
        last_mousex = mousex = parseInt(e.clientX - canvasx);
        last_mousey = mousey = parseInt(e.clientY - canvasy);
        mousedown = true;
    }

    handleMouseUp(e) {
        mousedown = false;

        let msg = {
            userId: this.props.userId,
            roomId: this.props.roomId,
            clickX : last_mousex,
            clickY : last_mousey
        };

        this.props.handleCanvasData(msg);
    }

    handleMouseMove(e) {
        mousex = parseInt(e.clientX-canvasx);
        mousey = parseInt(e.clientY-canvasy);

        if( mousedown ) {
            clickX.push(last_mousex);
            clickX.push(last_mousey);

            last_mousex = mousex;
            last_mousey = mousey;
        }
    }

    // <div className="sketch-btn">
    //     <img src={"img/pencil-icon.png"} onClick={ () => this.useTool('draw') } />
    //     <img src={"img/eraser-icon.png"} onClick={ () => this.useTool('erase') } />
    // </div>

    render() {
        // 출제자가 아닐 때에는 canvas handle 함수를 비활성해야 할 듯
        // boolean 변수를 하나 만들어서 삼항 연산자로 제어...

        let canvas = (
            <canvas id="canvas" width="600" height="600">
                이 브라우저는 canvas를 지원하지 않는 브라우저입니다. 포기하시고 크롬을 사용하십시오.
            </canvas>
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
        }

        return(
            <div className="game-area">
                <div className="sket-round-info">
                    <h3>Round 1/10</h3>
                </div>

                <div className="sketch-area">

                    { canvas }

                    <div className="progress progress-bar-vertical">
                        <div className="progress-bar progress-bar-danger progress-bar-striped active"
                            role="progressbar" aria-valuenow="100"
                            aria-valuemin="0" aria-valuemax="100"
                            style={{"height": '100%'}}>
                            <span className="sr-only">&nbsp;</span>
                        </div>
                    </div>
                </div>

                <div className="sket-chatting">
                    <div className="chat-body overflow-scroll">

                    </div>
                    <div className="chat">
                        <input type="text" id="chat-talk" />
                        <button id="chat-btn"><p>전송</p></button>
                    </div>
                </div>
            </div>
        );
    }
}

GameArea.propTypes = propTypes;
GameArea.defaultProps = defaultProps;

export default GameArea;
