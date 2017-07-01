import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserProfile from '../Index/UserProfile';

const propTypes = {
    user: ReactPropTypes.object,
    isReady: ReactPropTypes.bool,
    roomId: ReactPropTypes.string,

    handleGetReady: ReactPropTypes.func,
    handleStartGame: ReactPropTypes.func
};

const defaultProps = {
    user: {},
    isReady: false,
    roomId: '',

    handleGetReady: () => createWarning('handleGetReady'),
    handleStartGame: () => createWarning('handleStartGame')
};

function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined in UserArea');
}


class UserArea extends Component {
    constructor(props) {
        super(props);
    }

    startGame() {
        if ( this.props.playerList.length <= 0 ) {
            alert("혼자서 놀지 마세요. 불쌍하잖습니까.")
            return;
        }

        this.props.handleStartGame(this.props.roomId, this.props.user.id);
    }

    render() {

        let profileImage;

        let isMaster = typeof this.props.user.id !== 'undefined'
                        && typeof this.props.master !== 'undefined'
                        && this.props.user.id === this.props.master

        if ( this.props.user.type === 'LOGIN_GUEST'
                || typeof this.props.user.picture === 'undefined'
                || this.props.user.picture === 'null' ) {
            profileImage = ( <img src={'img/guest-profile.png'} alt="error"/> )
        } else {
            profileImage = ( <img src={ this.props.user.picture } alt="error"/> )
        }

        // 방장이 아닌 플레이어일 때
        // 사용자 자신의 영역이라면 핸들링 함수 추가
        const player = (
            this.props.me
            ?
            (
                <div className="sket-score profile-score"
                    style={{'backgroundColor': this.props.color}}
                    onClick={ () => this.props.handleGetReady(this.props.roomId, this.props.user.id, !this.props.isReady) }
                >
                    <p>{this.props.isReady ? "준비" : "대기"}</p>
                </div>
            )
            :
            (
                <div className="sket-score profile-score"
                    style={{'backgroundColor': this.props.color}}>
                    <p>{this.props.user.isReady ? "준비" : "대기"}</p>
                </div>
            )
        )

        // 방장일 때
        // 사용자 자신의 영역이라면 핸들링 함수 추가
        const master = (
            this.props.me
            ?
            (
                <div className="sket-score profile-score"
                    style={{'backgroundColor': this.props.color}}
                    onClick={ () => this.startGame() }
                >
                    <p>시작</p>
                </div>
            )
            :
            (
                <div className="sket-score profile-score"
                    style={{'backgroundColor': this.props.color}}>
                    <p>방장</p>
                </div>
            )
        );

        const gameScore = (
            <div className="sket-score profile-score"
                style={{'backgroundColor': this.props.color}}>
                <p>{ this.props.score }</p>
            </div>
        )

        const beforeGame = isMaster ? master : player;

        const rendering = this.props.isPlay ? gameScore : beforeGame;

        if (!this.props.enable) {   // 방 인원에 제한이 있어서 X를 표시할 때
            return (
                <div className="sket-game-user">
                    <div className="game-profile profile-score no-player">
                        <h1>X</h1>
                    </div>

                    <div className="sket-score profile-score no-player"
                        style={{'backgroundColor': this.props.color}}>>
                        <h1>X</h1>
                    </div>
                </div>
            )
        }


        return(
            <div className="sket-game-user">
                <div className={(isMaster ? "room-master" : "") + " game-profile profile-score"}>
                    <div className="player-image">
                        { profileImage }
                    </div>

                    <div className="player-info">
                        <p>{ this.props.user.nick }</p>
                        <p>Lv.{ this.props.user.level }</p>
                    </div>
                </div>

                { rendering }
            </div>
        );
    }
}

UserArea.propTypes = propTypes;
UserArea.defaultProps = defaultProps;

export default UserArea;
