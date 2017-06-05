import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserProfile from '../Index/UserProfile';

const propTypes = {
    user: ReactPropTypes.object,
    isReady: ReactPropTypes.bool,
    roomId: ReactPropTypes.string,

    handleGetReady: ReactPropTypes.func
};

const defaultProps = {
    user: {},
    isReady: false,
    roomId: '',

    handleGetReady: () => createWarning('handleGetReady')
};

function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined in UserArea');
}


class UserArea extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const player = (
            this.props.me
            ?
            (
                <div className="sket-score"
                    onClick={ () => this.props.handleGetReady(this.props.roomId, this.props.user.id, !this.props.isReady) }
                >
                    {this.props.isReady ? "준비" : "대기"}
                </div>
            )
            :
            (
                <div className="sket-score">
                    {this.props.user.isReady ? "준비" : "대기"}
                </div>
            )
        )

        const master = (
            this.props.me
            ?
            (
                <div className="sket-score"
                    onClick={ () => this.props.handleGetReady(this.props.roomId, this.props.user.id, !this.props.isReady) }
                >
                    <p>시작</p>
                </div>
            )
            :
            (
                <div className="sket-score">
                    <p>방장</p>
                </div>
            )
        );

        return(
            <div className="sket-game-user">
                <div className="player-area">
                    <UserProfile
                        divStyle="sket-player"
                        user={ this.props.user }
                    />

                    { (this.props.user.id === this.props.master) ? master : player }
                </div>
            </div>
        );
    }
}

UserArea.propTypes = propTypes;
UserArea.defaultProps = defaultProps;

export default UserArea;
