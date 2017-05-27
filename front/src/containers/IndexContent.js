import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import UserProfile from '../components/Index/UserProfile';
import FuncButtonArea from '../components/Index/FuncButtonArea';
import RoomList from '../components/Index/RoomList';
import ConnectingUserList from '../components/Index/ConnectingUserList';



const propTypes = {
};

const defaultProps = {
};

class IndexContent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="sket-index">

                <button onClick={ this.props.handleLogout }>
                    로그아웃
                </button>
                <div className="container">
                    <UserProfile
                        user={ this.props.user }
                    />
                    <FuncButtonArea
                            handleCreateRoom={ this.props.handleCreateRoom }
                    />
                    <RoomList/>
                    <ConnectingUserList/>
                    <div class="logout">
                    </div>
                </div>

            </div>
        );
    }
}

IndexContent.propTypes = propTypes;
IndexContent.defaultProps = defaultProps;

export default IndexContent;
