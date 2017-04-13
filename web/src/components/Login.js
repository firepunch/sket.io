import React, { Component, PropTypes } from 'react';

import IndexModal from './IndexModal';

const propTypes = {
};

const defaultProps = {
};

class Login extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>
                Loginaa
                <IndexModal/>
            </div>
        );
    }
}

Login.propTypes = propTypes;
Login.defaultProps = defaultProps;

export default Login;
