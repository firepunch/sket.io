import React, { Component, PropTypes } from 'react';

import UserArea from '../components/Game/UserArea';
import GameArea from '../components/Game/GameArea';
import SystemArea from '../components/Game/SystemArea';


const propTypes = {
};

const defaultProps = {
};

class GameContent extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div className="game-content">
                <UserArea
                    direction="left-side"/>
                <GameArea/>
                <UserArea
                    direction="right-side"/>
            </div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

export default GameContent;
