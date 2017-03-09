'use strict';

const ERROR_MISSING_PATH_PARAM_DID = 'missing path parameter: did';
const ERROR_MISSING_PATH_PARAM_SID = 'missing path parameter: sid';

module.exports.logEvent = (TAG, event) => {
    console.log(TAG, 'event.path', event.path);
    console.log(TAG, 'event.pathParameters', event.pathParameters);
    console.log(TAG, 'event.body', event.body);
};

module.exports.isValidConfigUrl = (TAG, event, callback) => {
    if (!event.pathParameters['did']) {
        console.error(TAG, ERROR_MISSING_PATH_PARAM_DID);
        callback(new Error(ERROR_MISSING_PATH_PARAM_DID));
        return false;
    }

    if (!event.pathParameters['sid']) {
        console.error(TAG, ERROR_MISSING_PATH_PARAM_SID);
        callback(new Error(ERROR_MISSING_PATH_PARAM_SID));
        return false;
    }

    return true;
};

module.exports.isValidDeviceUrl = (TAG, event, callback) => {
    if (!event.pathParameters['did']) {
        console.error(TAG, ERROR_MISSING_PATH_PARAM_DID);
        callback(new Error(ERROR_MISSING_PATH_PARAM_DID));
        return false;
    }

    return true;
};

module.exports.isValidDeviceBody = (TAG, data, callback) => {
    if ((data.hasOwnProperty('did') && data.hasOwnProperty('sid') && data.hasOwnProperty('uid')) === false) {
        console.error(TAG, 'did or sid or uid is missing.');
        callback(new Error('did or sid or uid is missing.'));
        return false;
    }
    // if (!data['type'] || !data['country_code']) {
    //     console.error(TAG, 'type or country_code is missing');
    //     callback(new error('type or country_code is missing'));
    //     return false;
    // }

    return true;
};

module.exports.isValidUserBody = (TAG, data, callback) => {
    if (!data.hasOwnProperty('uid')) {
        console.error(TAG, 'uid is missing.');
        callback(new Error('uid is missing.'));
        return false;
    }

    return true;
};