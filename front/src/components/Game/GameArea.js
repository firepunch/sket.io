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
            <div className="game-area">
                <div className="sket-round-info">
                    <h3>Round 1/10</h3>
                </div>

                <div className="sketch-area">
                    캔버스
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
