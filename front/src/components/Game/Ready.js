import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class Ready extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>Ready</div>
        );
    }
}

Ready.propTypes = propTypes;
Ready.defaultProps = defaultProps;

export default Ready;
