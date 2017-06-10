import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import $ from 'jquery';
import Progress from 'react-progressbar';

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

var tooltype = 'draw';


class GameArea extends Component {
    constructor(props) {
        super(props);

        this.useTool = this.useTool.bind(this);

        this.handleMouseDown = this.handleMouseDown.bind(this);
        this.handleMouseUp = this.handleMouseUp.bind(this);
        this.handleMouseMove = this.handleMouseMove.bind(this);
    }

    componentDidMount() {
        //Variables
        canvas = document.getElementById('canvas');
        ctx = canvas.getContext('2d');

        canvasx = $("#canvas").offset().left;
        canvasy = $("#canvas").offset().top;
    }

    //Use draw|erase
    useTool(tool) {
        tooltype = tool; //update

    }

    handleMouseDown(e) {
        last_mousex = mousex = parseInt(e.clientX-canvasx);
        last_mousey = mousey = parseInt(e.clientY-canvasy);
        mousedown = true;
    }

    handleMouseUp(e) {
        mousedown = false;
    }

    handleMouseMove(e) {
        mousex = parseInt(e.clientX-canvasx);
        mousey = parseInt(e.clientY-canvasy);
        if(mousedown) {
            ctx.beginPath();
            if(tooltype=='draw') {
                ctx.globalCompositeOperation = 'source-over';
                ctx.strokeStyle = 'black';
                ctx.lineWidth = 3;
            } else {
                ctx.globalCompositeOperation = 'destination-out';
                ctx.lineWidth = 10;
            }
            ctx.moveTo(last_mousex,last_mousey);
            ctx.lineTo(mousex,mousey);
            ctx.lineJoin = ctx.lineCap = 'round';
            ctx.stroke();
        }
        last_mousex = mousex;
        last_mousey = mousey;
        //Output
        $('#output').html('current: '+mousex+', '+mousey+'<br/>last: '+last_mousex+', '+last_mousey+'<br/>mousedown: '+mousedown);
    }

    render() {
        return(
            <div className="game-area">
                <div className="sket-round-info">
                    <h3>Round 1/10</h3>
                </div>

                <div className="sketch-area">
                    <div className="sketch-btn">
                        <img src={"img/pencil-icon.png"} onClick={ () => this.useTool('draw') } />
                        <img src={"img/eraser-icon.png"} onClick={ () => this.useTool('erase') } />
                    </div>

                    <canvas id="canvas" width="600" height="600"
                        onMouseDown={ (evt) => this.handleMouseDown(evt) }
                        onMouseUp={ (evt) => this.handleMouseUp(evt) }
                        onMouseMove={ (evt) => this.handleMouseMove(evt) }>
                        이 브라우저는 canvas를 지원하지 않는 브라우저입니다. 포기하시고 크롬을 사용하십시오.
                    </canvas>

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
