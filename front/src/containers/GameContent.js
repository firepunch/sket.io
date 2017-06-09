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

        // 문제 출제자가 아닌 플레이어들은 프로필 영역이 회색으로 바뀌도록.
        // 출제 중인 플레이어만 붉은 색 배경으로.
        // 시작 / 대기 / 점수 영역의 색깔은 랜덤으로

        const colors = ['#2ECC71', '#E67E22', '#FFCE54', '#34495E'];

        return(
            <div className="game-root">
                <div className="exit-button">
                    <button className="action-button shadow animate red">
                        <p>나가기</p>
                    </button>
                </div>
                <div className="game-content">
                    <div className="player-area">
                        <UserArea
                            me={ true }
                            color={ colors[0] }
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
                            color={ colors[1] }
                            enable={ enable[0] }

                            user={ playerList[0] }
                            master={ this.props.roomInfo.roomMaster }
                        />

                        <UserArea
                            me={ false }
                            color={ colors[2] }
                            enable={ enable[1] }

                            user={ playerList[1] }
                            master={ this.props.roomInfo.roomMaster }
                        />

                        <UserArea
                            me={ false }
                            color={ colors[3] }
                            enable={ enable[2] }

                            user={ playerList[2] }
                            master={ this.props.roomInfo.roomMaster }
                        />
                    </div>

                    { this.props.isPlay ? <GameArea /> : '' }

                </div>
            </div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

export default GameContent;
