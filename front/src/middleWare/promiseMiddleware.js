import axios from 'axios';

export default () => {
    return next => action => {
        const { promise, type, ...rest } = action;
        next({ ...rest, type: "LOGIN_REQUEST" });
        return axios({
            method: promise.method,
            url: promise.url,
            data: promise.data
        })
        .then(result => {
            next({ ...rest, result, type: "LOGIN_SUCCESS" });
        });
        .catch(error => {
            next({ ...rest, error, type: "LOGIN_FAILURE" });
        });
    };
};
