import React, { Component, PropTypes } from 'react';

// import FuncButton from './FuncButton';
import IndexModal from './createRoomModal';


const propTypes = {
    modalType: React.PropTypes.number
};

const defaultProps = {
    modalType: 0
};

class FuncButtonArea extends Component {
    constructor(props) {
        super(props);
    }

    // <FuncButton
    //     modalType={this.props.modalType}/>
    // <FuncButton/>

    render() {
        return(
            <div id="sket-header" className="component-container">

                <div>
                    <div className="sket-button" id="craete-room">
                        <createRoomModal
                            modalName="방 생성"/>
                    </div>

                    <div className="sket-button" id="quick-start">
                        <button className="action-button shadow animate red">
                            빠른 시작
                        </button>
                    </div>

                    <div className="sket-button" id="ranking">
                        <button className="action-button shadow animate red">
                            랭킹
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
