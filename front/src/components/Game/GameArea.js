import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import $ from 'jquery';

const propTypes = {
};

const defaultProps = {
};


class GameArea extends Component {
    constructor(props) {
        super(props);
    }

    // addClick(x, y, dragging) {
    //     clickX.push(x);
    //     clickY.push(y);
    //     clickDrag.push(dragging);
    // }
    //
    // redraw() {  // 캔버스에 그려줌
    //     context.clearRect(0, 0, context.canvas.width, context.canvas.height); // Clears the canvas
    //     context.strokeStyle = "#df4b26";
    //     context.lineJoin = "round";
    //     context.lineWidth = 5;
    //
    //     for (var i = 0; i < clickX.length; i++) {
    //         context.beginPath();
    //         if (clickDrag[i] && i){
    //             context.moveTo(clickX[i - 1], clickY[i - 1]);
    //         } else {
    //             context.moveTo(clickX[i] - 1, clickY[i]);
    //         }
    //         context.lineTo(clickX[i], clickY[i]);
    //         context.closePath();
    //         context.stroke();
    //     }
    // }

    render() {
        // let context = document.getElementById('canvas').getContext("2d");
        // let clickX = new Array();   //
        // let clickY = new Array();
        // let clickDrag = new Array();
        // let paint;
        //
        // $('#canvas').mousedown(function(e){
        //         let mouseX = e.pageX - this.offsetLeft;
        //         let mouseY = e.pageY - this.offsetTop;
        //         paint = true;
        //         this.addClick(mouseX, mouseY, '');
        //         // webSocket.send(JSON.stringify(msg));
        //         this.redraw();
        //     });
        // $('#canvas').mousemove(function(e){
        //     if (paint) {    // mousedown을 한 상태에서 마우스를 움직일 때
        //         this.addClick(e.pageX - this.offsetLeft, e.pageY - this.offsetTop, true);
        //         // webSocket.send(JSON.stringify(msg));
        //         this.redraw();
        //     }
        // });
        //
        // /* mouseup을 하거나 마우스가 캔버스를 벗어났을 때 */
        // $('#canvas').mouseup(function(e){
        //     paint = false;
        // });
        // $('#canvas').mouseleave(function(e){
        //     paint = false;
        // });


        return(
            <div className="game-area">
                <div className="sket-round-info">
                    <h3>Round 1/10</h3>
                </div>

                <div className="sketch-area">
                    <canvas id="canvas" width="600" height="600" style="border:1px solid #000000;">
                        이 브라우저는 canvas를 지원하지 않는 브라우저입니다. 포기하시고 크롬을 사용하십시오.
                    </canvas>
                </div>

                <div className="time-progress-bar">
                    <div className="progressbar progressbar-blue">
                        <div className="progressbar-inner"></div>
                    </div>
                </div>

                <div className="sket-chatting">
                    chatting
                </div>
            </div>
        );
    }
}

GameArea.propTypes = propTypes;
GameArea.defaultProps = defaultProps;

export default GameArea;
