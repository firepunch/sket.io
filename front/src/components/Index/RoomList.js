import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class RoomList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const rooms = (
            <div>
                { this.props.roomList }
            </div>
        )

        const rank = (
            <div>
                { this.props.ranking }
            </div>
        )

        return(
            <div id="room-list" className="component-container index-right index-bottom">
                { this.props.isShowRanking }
            </div>
        );
    }
}

RoomList.propTypes = propTypes;
RoomList.defaultProps = defaultProps;

export default RoomList;
