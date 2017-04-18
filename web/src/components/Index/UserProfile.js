import React, { Component, PropTypes } from 'react';

const propTypes = {
    divStyle: React.PropTypes.string
};

const defaultProps = {
    divStyle: ''
};

class UserProfile extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const indexStyle = {
            width: '20%',
            height: '20%',
            backgroundColor: 'skyblue',

            float: 'left'
        }

        const gameStyle = {
            width: '100%',
            height: '50%',
            float: 'none',
            backgroundColor: 'lightgreen'
        };

        let divStyle = {};

        if (this.props.divStyle === 'sket-player') divStyle = gameStyle;
        else divStyle = indexStyle;

        return(
            <div className="sket-profile" style={divStyle}>
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
