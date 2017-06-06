import React, { Component, PropTypes } from 'react';
import { PropTypes as ReactPropTypes } from 'prop-types';

const propTypes = {
    divStyle: React.PropTypes.string,
    user: ReactPropTypes.object
};

const defaultProps = {
    divStyle: '',
    user: {
        nick: 'GUEST',
        level: 1,
        curExp: 0,
        limitExp: 300,
        totalExp: 0
    }
};

class UserProfile extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let profileImage;

        if (this.props.user.type === 'LOGIN_GUEST' || typeof this.props.user.picture === 'undefined') {
            profileImage = ( <img src={'img/guest-profile.png'} alt="error"/> )
        } else {
            profileImage = ( <img src={ this.props.user.picture } alt="error"/> )
        }

        return(
            <div id="sket-profile" className="component-container index-left index-top">
                <div className="profile-image">
                    { profileImage }
                </div>

                <div className="user-info">
                    <p>{ this.props.user.nick }</p>
                    <p>Lv.{ this.props.user.level }</p>
                    <p>{ this.props.user.curExp } / { this.props.user.limitExp }</p>
                </div>
            </div>
        );
    }
}

UserProfile.propTypes = propTypes;
UserProfile.defaultProps = defaultProps;

export default UserProfile;
