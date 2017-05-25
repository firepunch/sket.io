import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import GoogleLogin from 'react-google-login';
import FacebookLogin from 'react-facebook-login';
import Loading from 'react-loading';

import IndexContent from './IndexContent';
import GameContent from './GameContent';

import * as actions from '../actions';


const propTypes = {
};

const defaultProps = {
};

class Sket extends Component {
    constructor(props) {
        super(props);
    }

    render() {

        const loginPage = (
            <div className="sket-login">
                <div className="login-container">
                    <div className="login-header">
                        <h1>sket.io</h1>
                    </div>

                    <div className="login-content">

                        <div className="login-button">
                            <GoogleLogin
                                clientId="755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com"
                                className="action-button shadow animate red"
                                buttonText="구글로 로그인"
                                onSuccess={(res) => { this.props.handleLogin('google', res) }}
                                onFailure={(error) => { console.log(error) }}
                                onRequest={ this.props.handleLoginRequest }
                                offline={false}
                                />
                        </div>

                        <div className="login-button">
                            <FacebookLogin
                                appId="741189302727195"
                                autoLoad={true}
                                fields="name,email,picture"
                                cssClass="action-button shadow animate blue"
                                textButton="페이스북으로 로그인"
                                onClick={ this.props.handleLoginRequest }
                                callback={(res) => { this.props.handleLogin('facebook', res) }}
                                />
                        </div>

                        <div className="login-button">
                            <button onClick={ this.props.handleGuestLogin }
                                className="action-button shadow animate green"
                                >
                                GUEST로 로그인하기
                            </button>
                        </div>

                    </div>
                </div>
            </div>
        )

        const index = (
                <IndexContent
                    user={this.props.user}
                    handleCreateRoom={this.props.handleCreateRoom}
                />
        )

        const game = (
                <GameContent/>
        )

        const rendering = ( this.props.isLoggedIn ? index : loginPage );

        const loading = (<Loading type="cylon" color="white"
                            height='667' width='375' className="loading-svg"/>)

        return(
            <div className="sket-root">
                {
                    this.props.fetchingUpdate ? loading : rendering
                }
            </div>
        );
    }
}

Sket.propTypes = propTypes;
Sket.defaultProps = defaultProps;


const mapStateToProps = (state) => {
    const { isLoggedIn, fetchingUpdate, user } = state.login;

    return {
        isLoggedIn,
        fetchingUpdate,
        user,
        usage: state.modal
    };
}


const mapDispatchToProps = (dispatch) => {
    return {
        /* 로그인 핸들링 */
        handleLoginRequest: () => { dispatch(actions.requestLogin()) },
        handleLogin: (social, user) => { dispatch(actions.handleLogin(social, user)) },
        handleGuestLogin: () => { dispatch(actions.handleGuestLogin()) },

        /* 대기 화면 핸들링 */
        handleCreateRoom: (roomInfo) => { dispatch(actions.createRoom(roomInfo)) }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Sket);
