import React, { Component, PropTypes } from 'react';

import UserProfile from '../Index/UserProfile';
import Ready from './Ready';
import Score from './Score';

const propTypes = {
    direction: React.PropTypes.string,
    isReady: React.PropTypes.bool,
    getReady: React.PropTypes.func
};

const defaultProps = {
    direction: '',
    isReady: false,
    getReady: () => createWarning('getReady')
};

function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined');
}

class UserArea extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        return(
            <div className="sket-game-side" id={this.props.direction}>
                <div className="player-area">
                    <UserProfile
                        divStyle="sket-player"/>

                    <div className="sket-score"
                        onClick={this.props.getReady}
                    >
                        {this.props.isReady ? "대기" : "준비"}
                    </div>
                </div>
                <div className="player-area">
                    <div className="sket-score"
                        onClick={this.props.getReady}
                    >
                        {this.props.isReady ? "대기" : "준비"}
                    </div>
                    <div className="sket-player">

                    </div>
                </div>
            </div>
        );
    }
}

UserArea.propTypes = propTypes;
UserArea.defaultProps = defaultProps;

export default UserArea;
