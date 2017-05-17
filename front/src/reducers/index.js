import { combineReducers } from 'redux';

import { routerReducer } from 'react-router-redux'
import { authStateReducer } from "redux-auth";

import login from './login';
import game from './game';
import modal from './modal';
import main from './main';
// reducer 를 여러 파일에 나누어 저장하였으므로 묶어서 export 해주어야 함

const reducers = combineReducers({
    login,
    game,
    modal,
    main,
    routing: routerReducer,
    auth: authStateReducer
})

export default reducers;
