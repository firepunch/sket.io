import React, { Component, PropTypes } from 'react';
import { Provider } from 'react-redux';

import { Router, Route, browserHistory } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import { syncHistoryWithStore } from 'react-router-redux';

import configureStore from '../configureStore';


import Sket from './Sket';
import IndexContent from './IndexContent'

const store = configureStore();
const history = syncHistoryWithStore(createBrowserHistory(), store);

// history.listen((location) => {
//   const path = (/#(\/.*)$/.exec(location.hash) || [])[1];
//   if (path) history.replace(path);
// });

const propTypes = {
};

const defaultProps = {
};

class App extends Component {
    constructor(props) {
        super(props);
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
