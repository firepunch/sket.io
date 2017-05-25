import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import GoogleLogin from 'react-google-login';
import FacebookLogin from 'react-facebook-login';

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

        this.handleLoginRequest = this.props.handleLoginRequest.bind(this);
        this.handleLogin = this.props.handleLogin.bind(this);
    }

    componentDidMount() {
        const { dispatch } = this.props;
    }

    render() {
        const { dispatch, isLoggedIn, fetchingUpdate, user } = this.props;

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
                            <button onClick={() => this.props.handleLogin('guest', '')}
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
                />
        )

        const game = (
                <GameContent/>
        )


        return(
            <div className="sket-root">
                {this.props.isLoggedIn ? index : loginPage }
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
        handleModalUsage: (usage) => { dispatch(actions.changeModalUsage(usage)) },
        handleLoginRequest: () => { dispatch(actions.requestLogin()) },
        handleLogin: (social, user) => { dispatch(actions.handleLogin(social, user)) }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Sket);
