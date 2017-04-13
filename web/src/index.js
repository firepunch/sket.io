import React from 'react';
import ReactDOM from 'react-dom';

import { createStore } from 'redux';
import { Provider } from 'react-redux';

import App from './containers/App';

ReactDOM.render(
  <App />,
  document.getElementById('root')
);
