import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class FuncButton extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>FuncButton</div>
        );
    }
}

FuncButton.propTypes = propTypes;
FuncButton.defaultProps = defaultProps;

export default FuncButton;
