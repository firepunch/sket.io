import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class GameArea extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>GameArea</div>
        );
    }
}

GameArea.propTypes = propTypes;
GameArea.defaultProps = defaultProps;

export default GameArea;
