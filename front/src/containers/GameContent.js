import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserArea from '../components/Game/UserArea';
import GameArea from '../components/Game/GameArea';
import SystemArea from '../components/Game/SystemArea';

const propTypes = {
    user: ReactPropTypes.object,
    userList: ReactPropTypes.array,

    isReady: ReactPropTypes.bool,

    handleGetReady: ReactPropTypes.func
};

const defaultProps = {
    user: {},
    userList: [{}, {}, {}],

    isReady: false,

    handleGetReady: () => createWarning('handleGetReady')
};


function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined in GameContent');
}

class GameContent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="game-content">
                <UserArea
                    componentClass="game-left game-top"

                    user={ this.props.user }
                    roomId={ this.props.roomInfo.roomId }

                    isReady={ this.props.isReady }

                    handleGetReady={ this.props.handleGetReady }
                />

                <UserArea
                    componentClass="game-right game-bottom"
                    user={ this.props.userList[0] }
                    handleGetReady={ this.props.handleGetReady }
                />

                <UserArea
                    componentClass="game-left game-top"
                    user={ this.props.userList[1] }
                    handleGetReady={ this.props.handleGetReady }
                />

                <UserArea
                    componentClass="game-right game-bottom"
                    user={ this.props.userList[2] }
                    handleGetReady={ this.props.handleGetReady }
                />

            </div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

export default GameContent;
