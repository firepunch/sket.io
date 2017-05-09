import React, { Component, PropTypes } from 'react';

import Round from './Round';
import Quiz from './Quiz';
import Timer from './Timer';
import Chatting from './Chatting';

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
                <Round/>
                <Quiz/>
                <Timer/>
                <Chatting/>
            </div>
        );
    }
}

GameArea.propTypes = propTypes;
GameArea.defaultProps = defaultProps;

export default GameArea;
