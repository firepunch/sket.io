import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class Score extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>Score</div>
        );
    }
}

Score.propTypes = propTypes;
Score.defaultProps = defaultProps;

export default Score;
