import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

import UserProfile from '../components/Index/UserProfile';
import FuncButtonArea from '../components/Index/FuncButtonArea';
import RoomList from '../components/Index/RoomList';
import ConnectingUserList from '../components/Index/ConnectingUserList';


function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined in IndexContent');
}

const propTypes = {
    user: ReactPropTypes.object,

    userList: ReactPropTypes.array,
    roomList: ReactPropTypes.array,
    ranking: ReactPropTypes.object,

    handleLogout: ReactPropTypes.func,
    handleCreateRoom: ReactPropTypes.func,
    handleQuickStart: ReactPropTypes.func,
    handleShowRanking: ReactPropTypes.func,
    handleEnterRoom: ReactPropTypes.func,

    isShowRanking: ReactPropTypes.bool
};

const defaultProps = {
    user: {},

    userList: [],
    roomList: [],
    ranking: {},

    handleLogout: createWarning('handleLogout'),
    handleCreateRoom: createWarning('handleCreateRoom'),
    handleQuickStart: createWarning('handleQuickStart'),
    handleShowRanking: createWarning('handleShowRanking'),
    handleEnterRoom: createWarning('handleEnterRoom'),

    isShowRanking: false
};

class IndexContent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="sket-index">
                <div className="container">
                    <UserProfile
                        user={ this.props.user }
                    />

                    <FuncButtonArea
                        handleCreateRoom={ this.props.handleCreateRoom }
                        handleQuickStart={ this.props.handleQuickStart }
                        handleShowRanking={ this.props.handleShowRanking }
                        isShowRanking={ this.props.isShowRanking }
                        userId={ this.props.user.id }
                    />

                    <ConnectingUserList
                        userList={ this.props.userList }
                    />

                    <RoomList
                        roomList={ this.props.roomList }
                        ranking={ this.props.ranking }
                        isShowRanking={ this.props.isShowRanking }
                        handleEnterRoom={ this.props.handleEnterRoom }
                        userId={ this.props.user.id }
                    />
                </div>

            </div>
        );
    }
}

IndexContent.propTypes = propTypes;
IndexContent.defaultProps = defaultProps;

export default IndexContent;
