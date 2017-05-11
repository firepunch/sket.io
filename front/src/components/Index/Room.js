import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class Room extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>Room</div>
        );
    }
}

Room.propTypes = propTypes;
Room.defaultProps = defaultProps;

export default Room;
