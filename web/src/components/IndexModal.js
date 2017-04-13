import React, { Component, PropTypes } from 'react';
import Modal from 'react-modal';


const propTypes = {
};

const defaultProps = {
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
                    <button onClick={this.handleOpenModal}>Trigger Modal</button>
                    <Modal
                       isOpen={this.state.showModal}
                       contentLabel="Minimal Modal Example">
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
