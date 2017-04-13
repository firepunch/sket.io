import React, { Component, PropTypes } from 'react';

import ConnectingUser from './ConnectingUser';

const propTypes = {
};

const defaultProps = {
};

class ConnectingUserList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div id="connection-list">
                <div>
                    ConnectingUserList
                </div>
                <ConnectingUser/>
            </div>
        );
    }
}

ConnectingUserList.propTypes = propTypes;
ConnectingUserList.defaultProps = defaultProps;

export default ConnectingUserList;
