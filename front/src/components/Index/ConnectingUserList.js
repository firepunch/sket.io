import React, { Component } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

const propTypes = {
    userList: ReactPropTypes.array
};

const defaultProps = {
    userList: []
};

class ConnectingUserList extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        let userListInfo = (
            <div className="user-list">
                <p>접속 중인 유저가 없습니다.</p>
            </div>
        );

        if (this.props.userList.length > 0) {
            userListInfo = this.props.userList.map((a, index) => {
                return (
                    <div className="user-list">
                        <p>{ a.level }</p>
                        <p>{ a.playerId }</p>
                    </div>
                )
            })
        }

        return(
            <div id="connection-list" className="component-container index-left index-bottom">
                { userListInfo }
            </div>
        );
    }
}

ConnectingUserList.propTypes = propTypes;
ConnectingUserList.defaultProps = defaultProps;

export default ConnectingUserList;
