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

        // onSubmit={}

        const loginModal = (
            <form method="post">
                <div class="modal-body">
                    <div>
                        <button class="loginBtn loginBtn-facebook" name="loginBtn" value="fb">
                            Login with Facebook
                        </button>

                        <button class="loginBtn loginBtn-google" name="loginBtn" value="google">
                            Login with Google
                        </button>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-lg signup-form-signup">SIGN UP</button>
                </div>
            </form>
        )

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

                        {this.props.modalUsage === "LOGIN" ? loginModal : createRoomModal}

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
