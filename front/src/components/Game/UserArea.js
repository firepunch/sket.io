import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserProfile from '../Index/UserProfile';

const propTypes = {
    isReady: ReactPropTypes.bool,
    getReady: ReactPropTypes.func
};

const defaultProps = {
    isReady: false,
    getReady: () => createWarning('getReady')
};

function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined in UserArea');
}

class UserArea extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        return(
            <div className="sket-game-user">
                <div className="player-area">
                    <UserProfile
                        divStyle="sket-player"
                        user={ this.props.user }
                    />

                    <div className="sket-score"
                        onClick={this.props.getReady}
                    >
                        {this.props.isReady ? "대기" : "준비"}
                    </div>
                </div>
            </div>
        );
    }
}

UserArea.propTypes = propTypes;
UserArea.defaultProps = defaultProps;

export default UserArea;
