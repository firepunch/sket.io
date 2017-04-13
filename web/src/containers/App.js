import React, { Component, PropTypes } from 'react';

import IndexContent from './IndexContent';

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
            <IndexContent/>
        );
    }
}

App.propTypes = propTypes;
App.defaultProps = defaultProps;

export default App;
