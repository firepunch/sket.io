import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

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

    componentDidMount() {
        const { dispatch } = this.props;
    }

    render() {
        const { dispatch, isLoggedIn, fetchingUpdate, user } = this.props;
        const loginPage = (
            <div className="login-container">
                <div className="login-header">
                    <h1>sket.io</h1>
                </div>

                <div className="login-content">
                    <div className="login-button">

                        <button onClick={() => this.props.handleLogin('facebook')}
                            className="action-button shadow animate blue"
                            >
                            페이스북으로 로그인하기
                        </button>
                        <a href="http://www.facebook.com/dialog/oauth?client_id=741189302727195&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2F">
                        </a>
                    </div>

                    <div className="login-button">
                        <button onClick={() => this.props.handleLogin('google')}
                            className="action-button shadow animate red"
                            >
                            구글로 로그인하기
                        </button>
                        <a href="https://accounts.google.com/o/oauth2/auth?client_id=755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com&redirect_uri=http://localhost:8080/&scope=https://www.googleapis.com/auth/plus.login&response_type=code">
                        </a>
                    </div>

                    <div className="login-button">
                        <button onClick={() => this.props.handleLogin('guest')}
                            className="action-button shadow animate green"
                        >
                            GUEST로 로그인하기
                        </button>
                    </div>

                    <button onClick={() => fetchingUpdate}>테스트</button>
                </div>

            </div>
        )


        const index = (
                <IndexContent/>
        )

        const game = (
                <GameContent/>
        )

        // const content = (
        //     {this.props.}
        // )

        return(
            <div className="content">
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
        handleLogin: (social) => { dispatch(actions.handleLogin(social)) }
        // handleFacebookLogin: () => { dispatch(actions.loginFacebook()) },
        // handleGoogleLogin: () => { dispatch(actions.loginGoogle()) },
        // handleGuestLogin: () => { dispatch(actions.loginGeust()) }
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Sket);
