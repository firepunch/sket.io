import React, { Component, PropTypes } from 'react';

import Room from './Room';

const propTypes = {
};

const defaultProps = {
};

class RoomList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div id="room-list">
                <div>RoomList</div>
                <Room/>
            </div>
        );
    }
}

RoomList.propTypes = propTypes;
RoomList.defaultProps = defaultProps;

export default RoomList;
