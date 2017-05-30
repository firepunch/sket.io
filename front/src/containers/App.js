import React, { Component } from 'react';
import { Provider } from 'react-redux';

import configureStore from '../configureStore';


import Sket from './Sket';
import ReduxModal from 'react-redux-modal'

const store = configureStore();


class App extends Component {
    render() {
        return(
            <Provider store={store}>
                <div className="wrapper">
                    <Sket />
                    <ReduxModal />
                </div>
            </Provider>
        );
    }
}

export default App;
