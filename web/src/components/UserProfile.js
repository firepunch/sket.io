import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class UserProfile extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>UserProfile</div>
        );
    }
}

UserProfile.propTypes = propTypes;
UserProfile.defaultProps = defaultProps;

export default UserProfile;
