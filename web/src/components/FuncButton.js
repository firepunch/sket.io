import React, { Component, PropTypes } from 'react';

const propTypes = {
};

const defaultProps = {
};

class FuncButton extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return(
            <div>
                <div class="sket-button" id="craete-room">
                    <button>방 생성</button>
                </div>
            </div>
        );
    }
}

FuncButton.propTypes = propTypes;
FuncButton.defaultProps = defaultProps;

export default FuncButton;
