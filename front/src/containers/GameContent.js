import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserArea from '../components/Game/UserArea';
import GameArea from '../components/Game/GameArea';
import SystemArea from '../components/Game/SystemArea';

const propTypes = {
    user: ReactPropTypes.object,
    roomInfo: ReactPropTypes.object,
    playerList: ReactPropTypes.array,

    isReady: ReactPropTypes.bool,

    handleGetReady: ReactPropTypes.func
};

const defaultProps = {
    user: {},
    roomInfo: {},
    playerList: [{}, {}, {}],

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

        let playerList = this.props.playerList;

        for (let player in playerList) {
            if (playerList[player].id === this.props.user.id) {
                console.log(playerList[player])
                playerList.splice(player, 1);
            }
        }

        return(
            <div className="game-content">
                <UserArea
                    componentClass="game-left game-top me"
                    me={ true }

                    user={ this.props.user }
                    roomId={ this.props.roomInfo.roomId }
                    master={ this.props.roomMaster }

                    isReady={ this.props.isReady }

                    handleGetReady={ this.props.handleGetReady }
                />

                <UserArea
                    componentClass="game-right game-bottom"
                    me={ false }
                    user={ playerList[0] }
                    master={ this.props.roomMaster }
                />

                <UserArea
                    componentClass="game-left game-top"
                    me={ false }
                    user={ playerList[1] }
                    master={ this.props.roomMaster }
                />

                <UserArea
                    componentClass="game-right game-bottom"
                    me={ false }
                    user={ playerList[2] }
                    master={ this.props.roomMaster }
                />

            </div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

export default GameContent;
