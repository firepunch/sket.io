import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';


const propTypes = {
    roomList: ReactPropTypes.array,
    ranking: ReactPropTypes.object,
    isShowRanking: ReactPropTypes.bool,
    userId: ReactPropTypes.string,

    handleEnterRoom: ReactPropTypes.func
};

const defaultProps = {
    roomList: [],
    ranking: {},
    isShowRanking: false,
    userId: '',

    handleEnterRoom: () => createWarning('handleEnterRoom')
};

function createWarning(funcName) {
    return () => console.warn(funcName + ' is not defined in RoomList');
}

function hexToRgb(hex) {
    // Expand shorthand form (e.g. "03F") to full form (e.g. "0033FF")
    var shorthandRegex = /^#?([a-f\d])([a-f\d])([a-f\d])$/i;
    hex = hex.replace(shorthandRegex, function(m, r, g, b) {
        return r + r + g + g + b + b;
    });

    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
    } : null;
}

class RoomList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        let rooms = (
            <div className="no-info">
                <p>방이 존재하지 않습니다</p>
            </div>
        )

        let rank = (
            <div className="no-info">
                <p>랭킹을 불러오는데 실패하였습니다.</p>
            </div>
        )

        let colors = ['#F5624D', '#34A65F', '#235E6F', '#ED5565', '#FFCE54', '#AC92EC', '#4FC1E9', '#EC87C0'];
        let random_color, color_style;

        if (this.props.roomList.length > 0) {
            rooms = this.props.roomList.map((a, index) => {
                random_color = colors[Math.floor(Math.random() * colors.length)];
                color_style = {
                    background: 'rgba('
                    + `${hexToRgb(random_color).r}, ${hexToRgb(random_color).g}, ${hexToRgb(random_color).b}, 0.9`
                    + ')'
                }
                return (
                    <div
                        className="room-info"
                        roomId={ a.roomId }
                        onClick={ () => this.props.handleEnterRoom(a.roomId, this.props.userId) }
                        style={ color_style }
                    >

                        <div className="room-info-row">
                            <span id="room-name-info">{ a.roomName }</span>
                            <br/><br/>
                            <span>{ '라운드 ' + a.roundLimit + ' / 제한시간 ' + a.timeLimit + '초'}</span>
                        </div>

                        <div className="room-info-row">
                            <span id="user-num">{ a.userNum + ' / ' + a.userNumLimit }</span>
                            <br/><br/>
                            <span>{ a.isLocked ? '비공개' : '공개' }</span>
                        </div>

                    </div>
                )
            });
        }

        if (this.props.ranking.hasOwnProperty('otherInfo')) {
            rank = (
                <div className="rank-info">
                    <div className="rank-header rank">
                        <span>#</span>
                        <span>레벨</span>
                        <span>닉네임</span>
                    </div>
                    {
                        ( this.props.ranking.myInfo.length <= 0 )
                        ?
                        (
                            <div className="my-rank rank"></div>
                        )
                        :
                        (
                            <div className="my-rank rank">
                                <span>{ this.props.ranking.myInfo[0].rank }</span>
                                <span>{ this.props.ranking.myInfo[0].level }</span>
                                <span>{ this.props.ranking.myInfo[0].nick }</span>
                            </div>
                        )
                    }
                    {
                        this.props.ranking.otherInfo.map((a, index) => {
                            return (
                                <div className="others-rank rank">
                                    <span>{ a.rank }</span>
                                    <span>{ a.level }</span>
                                    <span>{ a.nick }</span>
                                </div>
                            )
                        })
                    }
                </div>
            )
        }

        return(
            <div id="room-list" className="component-container index-right index-bottom overflow-scroll">
                { this.props.isShowRanking ? rank : rooms }
            </div>
        );
    }
}

RoomList.propTypes = propTypes;
RoomList.defaultProps = defaultProps;

export default RoomList;
