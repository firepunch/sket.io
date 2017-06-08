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
    
    render() {
        var context = document.getElementById('canvas').getContext("2d");
            var clickX = new Array();
            var clickY = new Array();
            var clickDrag = new Array();
            var paint;

        return(
            <div className="game-area">
                <div className="sket-round-info">
                    <h3>Round 1/10</h3>
                </div>

                <div className="sketch-area">
                    <canvas id="canvas" width="600" height="600" style="border:1px solid #000000;"></canvas>
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
