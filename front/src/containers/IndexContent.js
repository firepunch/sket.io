import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserProfile from '../components/Index/UserProfile';
import FuncButtonArea from '../components/Index/FuncButtonArea';
import RoomList from '../components/Index/RoomList';
import ConnectingUserList from '../components/Index/ConnectingUserList';


function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined');
}

const propTypes = {
    user: ReactPropTypes.object,

    userList: ReactPropTypes.array,
    roomList: ReactPropTypes.array,
    ranking: ReactPropTypes.array,

    handleLogout: ReactPropTypes.func,
    handleCreateRoom: ReactPropTypes.func
};

const defaultProps = {
    user: {},

    userList: [],
    roomList: [],
    ranking: [],

    handleLogout: createWarning('handleLogout'),
    handleCreateRoom: createWarning('handleCreateRoom')
};

class IndexContent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="sket-index">
                <div class="logout">
                    <button onClick={ this.props.handleLogout }>
                        로그아웃
                    </button>
                </div>
                <div className="container">
                    <UserProfile
                        user={ this.props.user }
                    />

                    <FuncButtonArea
                        handleCreateRoom={ this.props.handleCreateRoom }
                    />

                    <ConnectingUserList
                        userList={ this.props.userList }
                    />

                    <RoomList
                        roomList={ this.props.roomList }
                        ranking={ this.props.ranking }
                    />
                </div>

            </div>
        );
    }
}

IndexContent.propTypes = propTypes;
IndexContent.defaultProps = defaultProps;

export default IndexContent;
