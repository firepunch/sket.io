import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import Login from '../components/Index/Login';
import UserProfile from '../components/Index/UserProfile';
import FuncButtonArea from '../components/Index/FuncButtonArea';
import RoomList from '../components/Index/RoomList';
import ConnectingUserList from '../components/Index/ConnectingUserList';

import * as actions from '../actions';


const propTypes = {
};

const defaultProps = {
};

class IndexContent extends Component {
    constructor(props) {
        super(props);

        this.handleModalUsage = this.props.handleModalUsage.bind(this);
    }

    render() {
        return(
            <div className="content">
                <Login
                    onClick={ () => this.handleModalUsage("LOGIN")}
                    modalUsage={this.props.usage}
                    className="login"
                />

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

const mapStateToProps = (state) => {
    return {
        user: state.main.user,
        usage: state.modal.usage
        // isLoggedIn:
        // number: state.counter.number,
        // color: state.ui.color
    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        handleModalUsage: (usage) => { dispatch(actions.changeModalUsage(usage)) },
        handleFacebookLogin: () => { dispatch(actions.loginFacebook()) },
        handleGoogleLogin: () => { dispatch(actions.loginGoogle()) }
        // handleDecrement: () => { dispatch(actions.decrement()) },
        // handleSetColor: (color) => { dispatch(actions.setColor(color)) }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(IndexContent);
