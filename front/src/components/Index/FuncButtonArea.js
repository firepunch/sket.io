import React, { Component, PropTypes } from 'react';
import { modal } from 'react-redux-modal';
import { PropTypes as ReactPropTypes } from 'prop-types';

const propTypes = {
    handleCreateRoom: ReactPropTypes.func,
    handleQuickStart: ReactPropTypes.func,
    handleShowRanking: ReactPropTypes.func,
    isShowRanking: ReactPropTypes.bool,
    userId: ReactPropTypes.string
};

const defaultProps = {
    handleCreateRoom: createWarning('handleCreateRoom'),
    handleQuickStart: createWarning('handleQuickStart'),
    handleShowRanking: createWarning('handleShowRanking'),

    isShowRanking: false,

    userId: ''
};

function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined in FuncButtonArea');
}


class FuncButtonArea extends Component {
    constructor(props) {
        super(props);

        this.handleQuickStart = this.props.handleQuickStart.bind(this);
    }

    addModal() {
        modal.add(modalComponent, {
            title: '방 만들기',
            size: 'medium', // large, medium or small,
            closeOnOutsideClick: true, // (optional) Switch to true if you want to close the modal by clicking outside of it,
            hideTitleBar: false, // (optional) Switch to true if do not want the default title bar and close button,
            hideCloseButton: false, // (optional) if you don't wanna show the top right close button
            //.. all what you put in here you will get access in the modal props ;)
            handleCreateRoom: this.props.handleCreateRoom,   // 방 생성을 위한 함수
        });
    }

    render() {
        return(
            <div id="sket-header" className="component-container index-right index-top">

                <div>
                    <div className="sket-button" id="craete-room">
                        <button className="action-button shadow animate blue"
                                onClick={ this.addModal.bind(this) } >
                            <p>방 만들기</p>
                        </button>
                    </div>

                    <div className="sket-button" id="quick-start">
                        <button className="action-button shadow animate green"
                                onClick={ this.props.handleQuickStart }>
                            <p>빠른 시작</p>
                        </button>
                    </div>

                    <div className="sket-button" id="ranking">
                        <button className="action-button shadow animate yellow"
                                onClick={ () => this.props.handleShowRanking(this.props.userId) }>
                            <p>{ this.props.isShowRanking ? "방 목록" : "랭킹" }</p>
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

class modalComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            roomName: '',
            playerNumber: '',
            roundNumber: '',
            timeLimit: '',
            password: ''
        };

        /* change 이벤트 바인딩 */
        this.handleChangeRoomName = this.handleChangeRoomName.bind(this);
        this.handleChangePlayerNumber = this.handleChangePlayerNumber.bind(this);
        this.handleChangeRoundNumber = this.handleChangeRoundNumber.bind(this);
        this.handleChangeTimeLimit = this.handleChangeTimeLimit.bind(this);
        this.handleChangeRoomPassword = this.handleChangeRoomPassword.bind(this);

        this.createRoom = this.createRoom.bind(this);
    }

    removeThisModal() {
        this.props.removeModal();
    }

    /* input의 값이 변경될 때마다 반영 */
    handleChangeRoomName(evt) {
        this.setState({
            ...this.state,
            roomName: evt.target.value
        });
    }

    handleChangePlayerNumber(evt) {
        this.setState({
            ...this.state,
            playerNumber: evt.target.value
        })
    }

    handleChangeRoundNumber(evt) {
        this.setState({
            ...this.state,
            roundNumber: evt.target.value
        })
    }

    handleChangeTimeLimit(evt) {
        this.setState({
            ...this.state,
            timeLimit: evt.target.value
        })
    }

    handleChangeRoomPassword(evt) {
        this.setState({
            ...this.state,
            password: evt.target.value
        })
    }

    createRoom() {
        this.props.handleCreateRoom({
            ...this.state,
            isLocked: !(this.state.password == null)
        });

        this.removeThisModal();
    }

    render() {
        return (
            <div>

                <form>
                    <div className="room-configure">
                        <div className="row">
                            <span>
                                <input className="gate" id="room-name" type="text" required
                                    placeholder="방 이름" onChange={this.handleChangeRoomName}/>
                                <label htmlFor="room-name">방 이름</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="player-number" type="number" required
                                    placeholder="최대 4명까지 가능" onChange={this.handleChangePlayerNumber}/>
                                <label htmlFor="player-number">플레이어 수</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="round-number" type="number" required
                                    placeholder="최대 10 라운드까지 가능" onChange={this.handleChangeRoundNumber}/>
                                <label htmlFor="round-number">라운드 수</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="time-limit" type="number" required
                                    placeholder="최대 30초까지 설정할 수 있습니다(임시)" onChange={this.handleChangeTimeLimit}/>
                                <label htmlFor="time-limit">제한 시간</label>
                            </span>
                        </div>

                        <div className="row">
                            <span>
                                <input className="gate" id="room-password" type="text"
                                    placeholder="최대 10자까지 설정할 수 있습니다(임시)" onChange={this.handleChangeRoomPassword}/>
                                <label htmlFor="room-password">비밀번호</label>
                            </span>
                        </div>
                    </div>
                </form>

                <div className="craete-room-modal-button">
                    <button
                        type="button"
                        className="action-button shadow animate blue"
                        onClick={ this.createRoom }>
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

FuncButtonArea.propTypes = propTypes;
FuncButtonArea.defaultProps = defaultProps;

export default FuncButtonArea;
