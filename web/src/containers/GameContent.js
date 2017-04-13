import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class GameContent extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>GameContent</div>
        );
    }
}

GameContent.propTypes = propTypes;
GameContent.defaultProps = defaultProps;

export default GameContent;
