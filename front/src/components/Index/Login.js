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
        // modalType 이 0이면 login 버튼을 보여줌
        // const login = (
        //     <div>
        //         <IndexModal
        //             modalName="로그인"/>
        //     </div>
        // )

        return(
            <div>
                <IndexModal
                    modalUsage={this.props.modalUsage}
                    modalName="로그인"
                />
            </div>
        );
    }
}

Login.propTypes = propTypes;
Login.defaultProps = defaultProps;

export default Login;
