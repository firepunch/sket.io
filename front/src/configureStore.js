import { createStore, applyMiddleware } from 'redux';
import { createLogger } from 'redux-logger';    // redux actions logger

import rootReducer from './reducers';

import { routerReducer } from 'react-router-redux'

import thunkMiddleware from 'redux-thunk';  // 비동기 통신 미들웨어
import socketMiddleware from './middleWare/socketMiddleware'
const loggerMiddleware = createLogger();

const createStoreWithMiddleware = applyMiddleware(
    thunkMiddleware,
    loggerMiddleware,
    socketMiddleware
)(createStore);

export default function configureStore(initialState) {
    return createStoreWithMiddleware(rootReducer, initialState);
}
