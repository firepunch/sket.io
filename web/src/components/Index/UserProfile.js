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
            <div id="sket-profile">
                <div className="profile-image">
                    <img src={"../logo.svg"} alt="error"/>
                </div>

                <div className="user-info">
                    GUEST7777
                </div>
                <div className="user-info">
                    Lv.1
                </div>
                <div className="user-info">
                    exp bar
                </div>
            </div>
        );
    }
}

UserProfile.propTypes = propTypes;
UserProfile.defaultProps = defaultProps;

export default UserProfile;
