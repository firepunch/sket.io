import React, { Component, PropTypes } from 'react';
import { Provider } from 'react-redux';
import configureStore from '../configureStore';

import Sket from './Sket';

const store = configureStore();

const propTypes = {
};

const defaultProps = {
};

class App extends Component {
    constructor(props) {
        super(props);

        // this.state = {
        //     isIndex: true
        // }
        //
        // this.handleTest = this.handleTest.bind(this);
    }

    // handleTest() {  // 테스트용 핸들 함수
    //     this.setState({
    //         isIndex: !this.state.isIndex
    //     });
    //
    //     console.log(this.state.isIndex);
    // }

    render() {
        return(
            <Provider store={store}>
                <Sket />
            </Provider>
        );
    }
}


App.propTypes = propTypes;
App.defaultProps = defaultProps;

export default App;
