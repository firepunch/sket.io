import React, { Component, PropTypes } from 'react';
import { modal } from 'react-redux-modal';

// import FuncButton from './FuncButton';


const propTypes = {
    modalType: React.PropTypes.number
};

const defaultProps = {
    modalType: 0
};


class modalComponent extends Component {
    constructor(props) {
        super(props);
        console.log('## MODAL DATA AND PROPS:', this.props);
    }

    removeThisModal() {
        this.props.removeModal();
    }

    render() {
        return (
            <div>
                <p>this is my modal</p>
                <button
                    type="button"
                    onClick={this.removeThisModal.bind(this)}>
                    close this modal
                </button>
            </div>
        );
    }
}

class FuncButtonArea extends Component {
    constructor(props) {
        super(props);
    }

    addModal() {
        modal.add(modalComponent, {
            title: '방 생성',
            size: 'large', // large, medium or small,
            closeOnOutsideClick: true, // (optional) Switch to true if you want to close the modal by clicking outside of it,
            hideTitleBar: false, // (optional) Switch to true if do not want the default title bar and close button,
            hideCloseButton: false // (optional) if you don't wanna show the top right close button
            //.. all what you put in here you will get access in the modal props ;)
        });
    }

    render() {
        return(
            <div id="sket-header" className="component-container">

                <div>
                    <div className="sket-button" id="craete-room">
                        <button onClick={this.addModal.bind(this)}
                                className="action-button shadow animate red">
                            <p>방 생성</p>
                        </button>
                    </div>

                    <div className="sket-button" id="quick-start">
                        <button className="action-button shadow animate red">
                            <p>빠른 시작</p>
                        </button>
                    </div>

                    <div className="sket-button" id="ranking">
                        <button className="action-button shadow animate red">
                            <p>랭킹</p>
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

FuncButtonArea.propTypes = propTypes;
FuncButtonArea.defaultProps = defaultProps;

export default FuncButtonArea;
