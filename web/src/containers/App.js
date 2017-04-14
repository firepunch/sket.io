import React, { Component, PropTypes } from 'react';

import IndexContent from './IndexContent';
import GameContent from './GameContent';

const propTypes = {
};

const defaultProps = {
};

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isIndex: true
        }

        this.handleTest = this.handleTest.bind(this);
    }

    handleTest() {  // 테스트용 핸들 함수
        this.setState({
            isIndex: !this.state.isIndex
        });

        console.log(this.state.isIndex);
    }

    render() {
        const index = (
                <IndexContent/>
        )

        const game = (
                <GameContent/>
        )

        return(
            <div className="content">
                <button onClick={this.handleTest}>test</button>
                {this.state.isIndex ? index : game}
            </div>
        );
    }
}

App.propTypes = propTypes;
App.defaultProps = defaultProps;

export default App;
