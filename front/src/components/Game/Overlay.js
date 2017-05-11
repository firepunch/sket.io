import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class Overlay extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>Overlay</div>
        );
    }
}

Overlay.propTypes = propTypes;
Overlay.defaultProps = defaultProps;

export default Overlay;
