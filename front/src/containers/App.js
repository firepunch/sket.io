import React, { Component, PropTypes } from 'react';
import { Provider } from 'react-redux';

import configureStore from '../configureStore';


import Sket from './Sket';
import IndexContent from './IndexContent'

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
                <Sket />
            </Provider>
        );
    }
}


App.propTypes = propTypes;
App.defaultProps = defaultProps;

export default App;
