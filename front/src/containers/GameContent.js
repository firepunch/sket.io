import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserArea from '../components/Game/UserArea';
import GameArea from '../components/Game/GameArea';
import SystemArea from '../components/Game/SystemArea';

const propTypes = {
    user: ReactPropTypes.object,

    userList: ReactPropTypes.array
};

const defaultProps = {
    user: {},

    userList: [{}, {}, {}]
};

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
                />

                <UserArea
                    componentClass="game-right game-bottom"
                    user={ this.props.userList[0] }
                />

                <UserArea
                    componentClass="game-left game-top"
                    user={ this.props.userList[1] }
                />

                <UserArea
                    componentClass="game-right game-bottom"
                    user={ this.props.userList[2] }
                />

            </div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

export default GameContent;
