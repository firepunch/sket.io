import React, { Component, PropTypes } from 'react';

import FuncButton from './FuncButton';

const propTypes = {
};

const defaultProps = {
};

class FuncButtonArea extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>
                <div>
                    FuncButtonArea
                </div>
                <FuncButton/>
            </div>
        );
    }
}

FuncButtonArea.propTypes = propTypes;
FuncButtonArea.defaultProps = defaultProps;

export default FuncButtonArea;
