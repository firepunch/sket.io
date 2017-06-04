import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class GameArea extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div className="stage" id="game-area">
                <div id="sket-round-info">
                    <h3>1/10 라운드</h3>
                </div>

                <div id="sketch-area">

                </div>

                <div id="time-progress-bar">
                    <div className="progressbar progressbar-blue">
                        <div className="progressbar-inner"></div>
                    </div>
                </div>

                <div id="sket-chatting">
                    chatting
                </div>
            </div>
        );
    }
}

GameArea.propTypes = propTypes;
GameArea.defaultProps = defaultProps;

export default GameArea;
