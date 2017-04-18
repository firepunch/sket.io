import React, { Component, PropTypes } from 'react';

import IndexModal from './IndexModal';


const propTypes = {
    modalType: 0
};

const defaultProps = {
    // modalType: React.PropTypes.func
    // 왜 이게 있으면 삼항 연산자에서 무조건 true 가 될까?
};

class FuncButton extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const createRoom = (
            <div>
                <div class="sket-button" id="craete-room">
                    <IndexModal
                        modalName="방 생성"/>
                </div>
            </div>
        )

        const quickStart = (
            <div>
                <div class="sket-button" id="quick-start">
                    <button>빠른 시작</button>
                </div>
                <div class="sket-button" id="ranking">
                    <button>랭킹</button>
                </div>
            </div>
        )


        // modalType 가 0일 때 방 생성 모달
        // const isModal = this.props.modalType ? quickStart : createRoom;

        return(
            <div>
                {this.props.modalType ? createRoom : quickStart}
            </div>
        );
    }
}

FuncButton.propTypes = propTypes;
FuncButton.defaultProps = defaultProps;

export default FuncButton;
