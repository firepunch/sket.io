import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class Round extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div id="sket-round-info">
                <h3>1/10 라운드</h3>
            </div>
        );
    }
}

Round.propTypes = propTypes;
Round.defaultProps = defaultProps;

export default Round;
