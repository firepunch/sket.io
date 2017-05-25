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

                <form>
                    <div className="room-configure">
                        <div className="row">
                            <span>
                                <input className="gate" id="room-name" type="text" placeholder="방 이름" />
                                <label htmlFor="room-name">방 이름</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="player-number" type="text" placeholder="최대 4명까지 가능"  />
                                <label htmlFor="player-number">플레이어 수</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="round-number" type="text" placeholder="최대 10 라운드까지 가능" />
                                <label htmlFor="round-number">라운드 수</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="time-limit" type="text" placeholder="최대 30초까지 설정할 수 있습니다(임시)" />
                                <label htmlFor="time-limit">제한 시간</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="room-password" type="text" placeholder="최대 10자까지 설정할 수 있습니다(임시)" />
                                <label htmlFor="room-password">비밀번호</label>
                            </span>
                        </div>
                    </div>
                </form>

                <div className="craete-room-modal-button">
                    <button
                        type="button"
                        className="action-button shadow animate blue">
                        <p>확인</p>
                    </button>
                    <button
                        type="button"
                        onClick={this.removeThisModal.bind(this)}
                        className="action-button shadow animate red">
                        <p>취소</p>
                    </button>
                </div>
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
            title: '방 만들기',
            size: 'medium', // large, medium or small,
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
                                className="action-button shadow animate blue">
                            <p>방 만들기</p>
                        </button>
                    </div>

                    <div className="sket-button" id="quick-start">
                        <button className="action-button shadow animate green">
                            <p>빠른 시작</p>
                        </button>
                    </div>

                    <div className="sket-button" id="ranking">
                        <button className="action-button shadow animate yellow">
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
