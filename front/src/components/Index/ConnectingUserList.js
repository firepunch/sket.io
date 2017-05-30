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
                { this.props.userList }
            </div>
        );
    }
}

ConnectingUserList.propTypes = propTypes;
ConnectingUserList.defaultProps = defaultProps;

export default ConnectingUserList;
