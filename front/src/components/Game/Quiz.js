import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class Quiz extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div id="sketch-area">

            </div>
        );
    }
}

Quiz.propTypes = propTypes;
Quiz.defaultProps = defaultProps;

export default Quiz;
