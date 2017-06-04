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
            <div id="sket-profile" className="component-container index-left index-top" style={divStyle}>
                <div className="profile-image">
                    <img src={ this.props.user.picture } alt="error"/>
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
