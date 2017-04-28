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
            <div id="time-progress-bar">
                <div className="progressbar progressbar-blue">
                    <div className="progressbar-inner"></div>
                </div>
            </div>
        );
    }
}

Timer.propTypes = propTypes;
Timer.defaultProps = defaultProps;

export default Timer;
