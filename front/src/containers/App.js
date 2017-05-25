import React, { Component, PropTypes } from 'react';
import { Provider } from 'react-redux';

import configureStore from '../configureStore';


import Sket from './Sket';
import ReduxModal from 'react-redux-modal'

const store = configureStore();

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


App.propTypes = propTypes;
App.defaultProps = defaultProps;

export default App;
