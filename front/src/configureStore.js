import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';  // 비동기 통신 미들웨어
import { createLogger } from 'redux-logger';    // redux actions logger
import rootReducer from './reducers';

const loggerMiddleware = createLogger();

const createStoreWithMiddleware = applyMiddleware(
  thunkMiddleware,
  loggerMiddleware
)(createStore);

export default function configureStore(initialState) {
  return createStoreWithMiddleware(rootReducer, initialState);
}
