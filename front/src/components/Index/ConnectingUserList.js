import React, { Component, PropTypes } from 'react';

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
            <div id="connection-list" className="component-container index-left index-bottom">
                <div>
                    ConnectingUserList
                </div>
            </div>
        );
    }
}

ConnectingUserList.propTypes = propTypes;
ConnectingUserList.defaultProps = defaultProps;

export default ConnectingUserList;
