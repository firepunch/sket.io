import React, { Component, PropTypes } from 'react';

import Modal from 'react-modal';
import GoogleLogin from 'react-google-login';

const propTypes = {
    modalName: React.PropTypes.string
};

const defaultProps = {
    modalName: 'Modal Name'
};

class IndexModal extends Component {
    constructor () {
        super();
            this.state = {
                showModal: false
            };

        this.handleOpenModal = this.handleOpenModal.bind(this);
        this.handleCloseModal = this.handleCloseModal.bind(this);
    }

    handleOpenModal () {
        this.setState({ showModal: true });
    }

    handleCloseModal () {
        this.setState({ showModal: false });
    }

    render() {
        // const content = [
        //     <Login/>,
        //     <CreateRoom/>,
        //     <GameResult/>,
        //     <GameRanking/>
        // ]
        // modal 안에는 총 4가지의 내용이 올 수 있으므로
        // props 에 따라 렌더링함(가능할지는 모르겠음)

        // onSubmit={}

        const loginModal = (
                <div>
                    <a href="https://accounts.google.com/o/oauth2/auth?client_id=755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com&redirect_uri=http://localhost:8080/signup/google/&scope=https://www.googleapis.com/auth/plus.login&response_type=code"
                        value="google"
                        name="loginBtn">
                        구글로 로그인
                    </a>

                    <a name="loginBtn"
                        value="fb">
                        페이스북으로 로그인
                    </a>
                </div>
        )

        // <button class="loginBtn loginBtn-facebook" name="loginBtn" value="fb">
        //     Login with Facebook
        // </button>
        //
        // <GoogleLogin
        //     clientId="755801497962-25e8cmnp81pcld5r8mfsvmetus9qnnv4.apps.googleusercontent.com"
        //     buttonText="Login"
        // />
        //
        // <button class="loginBtn loginBtn-google" name="loginBtn" value="google">
        //     Login with Google
        // </button>

        const createRoomModal = (
            <div>

            </div>
        )

        return(
            <div>
                <div>
                    <button onClick={this.handleOpenModal}>{this.props.modalName}</button>
                    <Modal
                       isOpen={this.state.showModal}
                       contentLabel="Minimal Modal Example"
                    >
                        <div class="modal-body">
                            {this.props.modalUsage === "LOGIN" ? loginModal : createRoomModal}
                        </div>

                       <button onClick={this.handleCloseModal}>Close Modal</button>
                    </Modal>
                </div>
            </div>
        );
    }
}

IndexModal.propTypes = propTypes;
IndexModal.defaultProps = defaultProps;

export default IndexModal;
