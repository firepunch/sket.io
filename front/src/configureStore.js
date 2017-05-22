import { createStore, applyMiddleware } from 'redux';
import { createLogger } from 'redux-logger';    // redux actions logger

import rootReducer from './reducers';

import thunkMiddleware from 'redux-thunk';  // 비동기 통신 미들웨어
import socketMiddleware from './middleWare/socketMiddleware'
const loggerMiddleware = createLogger();

// 미들웨어는 여기서 작성한 파라미터 순으로 지정됨
// thunk - logger 순으로 미들웨어를 설정해야 비동기 action이 표시됨
const createStoreWithMiddleware = applyMiddleware(
    thunkMiddleware,
    loggerMiddleware,
    socketMiddleware
)(createStore);

export default function configureStore(initialState) {
    return createStoreWithMiddleware(rootReducer, initialState);
}
