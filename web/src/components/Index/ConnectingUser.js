import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class ConnectingUser extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>ConnectingUser</div>
        );
    }
}

ConnectingUser.propTypes = propTypes;
ConnectingUser.defaultProps = defaultProps;

export default ConnectingUser;
