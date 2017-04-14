import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class Timer extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>Timer</div>
        );
    }
}

Timer.propTypes = propTypes;
Timer.defaultProps = defaultProps;

export default Timer;
