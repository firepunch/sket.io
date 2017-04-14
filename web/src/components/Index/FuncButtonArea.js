import React, { Component, PropTypes } from 'react';

import FuncButton from './FuncButton';

const propTypes = {
    modalType: React.PropTypes.number
};

const defaultProps = {
    modalType: 0
};

class FuncButtonArea extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div id="sket-header">
                <FuncButton
                    modalType={this.props.modalType}/>
                <FuncButton/>
            </div>
        );
    }
}

FuncButtonArea.propTypes = propTypes;
FuncButtonArea.defaultProps = defaultProps;

export default FuncButtonArea;
