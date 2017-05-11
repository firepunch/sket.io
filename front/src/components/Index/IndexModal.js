import React, { Component, PropTypes } from 'react';
import Modal from 'react-modal';


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

        return(
            <div>
                <div>
                    <button onClick={this.handleOpenModal}>{this.props.modalName}</button>
                    <Modal
                       isOpen={this.state.showModal}
                       contentLabel="Minimal Modal Example"
                    >

                       <form action="signup.do" method="post">
                           <div class="modal-body">
                               <div>
                                   <input type="text" placeholder="ID" className="input signup-form__input" id="signup-form-id" name="id" required autofocus />
                                   <input type="password" placeholder="Password" id="signup-form-password" className="input signup-form__input" name="password" required />
                                   <input type="password" placeholder="Confirm password" id="signup-form-confirm" className="input signup-form__input" required />

                                   <form name="loginForm" action="LoginController" method="post">
                                       <button class="loginBtn loginBtn-facebook" name="loginBtn" value="fb">
                                           Login with Facebook
                                       </button>

                                       <button class="loginBtn loginBtn-google" name="loginBtn" value="google">
                                           Login with Google
                                       </button>
                                   </form>
                               </div>
                           </div>

                           <div class="modal-footer">
                               <button type="submit" class="btn btn-lg signup-form-signup">SIGN UP</button>
                           </div>
                       </form>

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
