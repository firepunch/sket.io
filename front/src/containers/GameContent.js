import React, { Component, PropTypes } from 'react';

import UserArea from '../components/Game/UserArea';
import GameArea from '../components/Game/GameArea';
import SystemArea from '../components/Game/SystemArea';

import { connect } from 'react-redux';
import * as actions from '../actions';

const propTypes = {
};

const defaultProps = {
};

class GameContent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="game-content">
                <UserArea
                    direction="left-side"
                    isReady={this.props.isReady}
                    getReady={this.props.handleGetReady}
                />
                <GameArea/>
                <UserArea
                    direction="right-side"
                    isReady={this.props.isReady}
                    getReady={this.props.handleGetReady}
                />
            </div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

// 여기서의 state 는 컴포넌트에서 사용하는 state와 다름
// redux의 state임
const mapStateToProps = (state) => {
    return {
        isReady: state.game.isReady
    };
}

// action을 dispatch하는 함수를 props에 연결
const mapDispatchToProps = (dispatch) => {
    // return bindActionCreators(actions, dispatch);
    return {
        handleGetReady: () => { dispatch(actions.getReady()) }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(GameContent);
