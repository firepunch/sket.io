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
        const loginPage = (
            <div className="login-container">
                <div className="login-header">
                    <h1>sket.io</h1>
                </div>

                <div className="login-content">
                    <div className="login-button">
                        <button onClick={() => this.props.handleLogin('FACEBOOK')}
                            className="action-button shadow animate blue"
                        >
                            페이스북으로 로그인하기
                        </button>
                    </div>

                    <div className="login-button">
                        <button onClick={() => this.props.handleLogin('GOOGLE')}
                            className="action-button shadow animate red"
                        >
                            구글로 로그인하기
                        </button>
                    </div>

                    <div className="login-button">
                        <button onClick={() => this.props.handleLogin('GUEST')}
                            className="action-button shadow animate green"
                        >
                            GUEST로 로그인하기
                        </button>
                    </div>
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
        usage: state.modal,
        // isLoggedIn:
        // number: state.counter.number,
        // color: state.ui.color
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
