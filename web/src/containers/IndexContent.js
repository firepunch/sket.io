import React, { Component, PropTypes } from 'react';

import Login from '../components/Index/Login';
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
            <div className="content">
                <Login
                    modalType={0}/>

                <div className="container">
                    <UserProfile />
                    <FuncButtonArea
                        modalType={1}/>
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
