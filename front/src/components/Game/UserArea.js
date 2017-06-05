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

    render() {

        const profileBackground = {
            backgroundColor: this.props.color[0]
        }

        const scoreBackground = {
            backgroundColor: this.props.color[1]
        }

        // 방장이 아닌 플레이어일 때
        // 사용자 자신의 영역이라면 핸들링 함수 추가
        const player = (
            this.props.me
            ?
            (
                <div className="sket-score profile-score" style={scoreBackground}
                    onClick={ () => this.props.handleGetReady(this.props.roomId, this.props.user.id, !this.props.isReady) }
                >
                    <p>{this.props.isReady ? "준비" : "대기"}</p>
                </div>
            )
            :
            (
                <div className="sket-score profile-score" style={scoreBackground}>
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
                <div className="sket-score profile-score" style={scoreBackground}
                    onClick={ () => this.props.handleStartGame(this.props.roomId, this.props.user.id) }
                >
                    <p>시작</p>
                </div>
            )
            :
            (
                <div className="sket-score profile-score" style={scoreBackground}>
                    <p>방장</p>
                </div>
            )
        );

        if (!this.props.enable) {
            return (
                <div className="sket-game-user">
                    <div className="game-profile profile-score" style={profileBackground}>
                        <h1>X</h1>
                    </div>

                    <div className="sket-score profile-score" style={scoreBackground}>
                        <h1>X</h1>
                    </div>
                </div>
            )
        }

        return(
            <div className="sket-game-user">
                <div className="game-profile profile-score" style={profileBackground}>
                    <div className="player-image">
                        <img src={ this.props.user.picture } alt="error"/>
                    </div>

                    <div className="player-info">
                        <p>{ this.props.user.nick }</p>
                        <p>Lv.{ this.props.user.level }</p>
                    </div>
                </div>

                {
                    (
                        typeof this.props.user.id !== 'undefined'
                        && typeof this.props.master !== 'undefined'
                        && this.props.user.id === this.props.master
                    )   ? master : player
                }
            </div>
        );
    }
}

UserArea.propTypes = propTypes;
UserArea.defaultProps = defaultProps;

export default UserArea;
