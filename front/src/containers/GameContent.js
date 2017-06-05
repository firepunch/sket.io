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

    handleGetReady: ReactPropTypes.func,
    handleStartGame: ReactPropTypes.func
};

const defaultProps = {
    user: {},
    roomInfo: {},
    playerList: [{}, {}, {}],

    isReady: false,

    handleGetReady: () => createWarning('handleGetReady'),
    handleStartGame: () => createWarning('handleStartGame')
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
        let enable = new Array(3);

        for (let player in playerList) {
            if (playerList[player].id === this.props.user.id) {
                playerList.splice(player, 1);
            }
        }

        for (let i in enable) {
            enable[i] = false;
        }

        for (let j = 0; j < this.props.roomInfo.userNumLimit - 1; j++) {
            enable[j] = true;
        }

        return(
            <div className="game-root">
                <div className="game-content">
                    <div className="player-area">
                        <UserArea
                            me={ true }
                            color={ ['#3498DB', '#82BF56'] }
                            enable={ true }

                            user={ this.props.user }
                            roomId={ this.props.roomInfo.roomId }
                            master={ this.props.roomInfo.roomMaster }

                            isReady={ this.props.isReady }

                            handleGetReady={ this.props.handleGetReady }
                            handleStartGame={ this.props.handleStartGame }
                            />

                        <UserArea
                            me={ false }
                            color={ ['#3498DB', '#82BF56'] }
                            enable={ enable[0] }

                            user={ playerList[0] }
                            master={ this.props.roomInfo.roomMaster }
                            />

                        <UserArea
                            me={ false }
                            color={ ['#3498DB', '#82BF56'] }
                            enable={ enable[1] }

                            user={ playerList[1] }
                            master={ this.props.roomInfo.roomMaster }
                            />

                        <UserArea
                            me={ false }
                            color={ ['#3498DB', '#82BF56'] }
                            enable={ enable[2] }

                            user={ playerList[2] }
                            master={ this.props.roomInfo.roomMaster }
                            />
                    </div>

                </div>
            </div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

export default GameContent;
