import React, { Component, PropTypes } from 'react';

import Login from '../components/Login';
import UserProfile from '../components/UserProfile';
import FuncButtonArea from '../components/FuncButtonArea';
import RoomList from '../components/RoomList';
import ConnectingUserList from '../components/ConnectingUserList';


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
            <div className="content">
                <Login/>
                <div className="container">
                    <UserProfile />
                    <FuncButtonArea/>
                    <RoomList/>
                    <ConnectingUserList/>
                </div>
            </div>
        );
    }
}

IndexContent.propTypes = propTypes;
IndexContent.defaultProps = defaultProps;

export default IndexContent;
