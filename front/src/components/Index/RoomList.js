import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';


const propTypes = {
    roomList: ReactPropTypes.array,
    ranking: ReactPropTypes.object,
    isShowRanking: ReactPropTypes.bool
};

const defaultProps = {
    roomList: [],
    ranking: {},
    isShowRanking: false
};

class RoomList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        let rooms = (
            <div>
                <p>방이 존재하지 않습니다</p>
            </div>
        )

        let rank = (
            <div>
                <p>랭킹을 불러오는데 실패하였습니다.</p>
            </div>
        )

        if (this.props.roomList.length > 0) {
            rooms = this.props.roomList.map((a, index) => {
                return (
                    <div className="room-info">
                        { a.roomName }
                        { a.timeLimit }
                        { a.userNumLimit }
                        { a.userNum }
                        { a.lock }
                    </div>
                )
            });
        }

        if (this.props.ranking.hasOwnProperty('otherInfo')) {
            rank = this.props.ranking.otherInfo.map((a, index) => {
                return (
                    <div className="rank-info">
                        <div className="my-rank">
                            <p>{ this.props.ranking.myInfo.rank }</p>
                            <p>{ this.props.ranking.myInfo.level }</p>
                            <p>{ this.props.ranking.myInfo.nick }</p>
                        </div>
                        <div className="others-rank">
                            <p>{ a.rank }</p>
                            <p>{ a.level }</p>
                            <p>{ a.nick }</p>
                        </div>
                    </div>
                )
            });
        }

        return(
            <div id="room-list" className="component-container index-right index-bottom">
                { this.props.isShowRanking ? rank : rooms }
            </div>
        );
    }
}

RoomList.propTypes = propTypes;
RoomList.defaultProps = defaultProps;

export default RoomList;
