import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

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

        // this.handleModalUsage = this.props.handleModalUsage.bind(this);
    }
    // <Login
    //     onClick={ () => this.handleModalUsage("LOGIN")}
    //     modalUsage={this.props.usage}
    //     className="login"
    //     />

    render() {
        return(
            <div className="content">

                <div className="container">
                    <UserProfile />
                    <FuncButtonArea
                        handleModal={this.handleModalUsage}
                    />
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
