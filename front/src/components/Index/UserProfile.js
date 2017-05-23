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
            // width: '20%',
            // height: '20%',
            //
            // float: 'left'
            // backgroundColor: 'skyblue',
        }

        const gameStyle = {
            // width: '100%',
            // height: '50%',
            // float: 'none'
            // backgroundColor: 'lightgreen'
        };

        let divStyle = {};

        if (this.props.divStyle === 'sket-player') divStyle = gameStyle;
        else divStyle = indexStyle;

        // <p>{this.props.user.name}</p>
        return(
            <div id="sket-profile" className="component-container" style={divStyle}>
                <div className="profile-image">
                    <img src={this.props.user.picture} alt="error"/>
                </div>

                <div className="user-info">
                    <p>GUEST10547</p>
                    <p>Lv.1</p>
                    <p>exp bar</p>
                </div>
            </div>
        );
    }
}

UserProfile.propTypes = propTypes;
UserProfile.defaultProps = defaultProps;

export default UserProfile;
