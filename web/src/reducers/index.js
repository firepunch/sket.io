import { combineReducers } from 'redux';
import login from './login';
// reducer 를 여러 파일에 나누어 저장하였으므로 묶어서 export 해주어야 함

const reducers = combineReducers({
    login
})

export default reducers;
