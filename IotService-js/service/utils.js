'use strict';


module.exports.logEvent = (TAG, event) => {
    console.log(TAG, 'event.path', event.path);
    console.log(TAG, 'event.pathParameters', event.pathParameters);
    console.log(TAG, 'event.body', event.body);
};

module.exports.isValidConfigUrl = (event, callback) => {
    if (!event.pathParameters['did']) {
        console.error('path parameter did is missing.');
        callback(new Error('path parameter did is missing.'));
        return false;
    }

    if (!event.pathParameters['sid']) {
        console.error('path parameter sid is missing.');
        callback(new Error('path parameter sid is missing.'));
        return false;
    }

    return true;
};

module.exports.isValidDeviceUrl = (event, callback) => {
    if (!event.pathParameters['did']) {
        console.error('path parameter did is missing.');
        callback(new Error('path parameter did is missing.'));
        return false;
    }

    return true;
};

module.exports.isValidDeviceBody = (data, callback) => {
    if ((data.hasOwnProperty('did') && data.hasOwnProperty('sid') && data.hasOwnProperty('uid')) === false) {
        console.error('did or sid or uid is missing.');
        callback(new Error('did or sid or uid is missing.'));
        return false;
    }

    return true;
};

module.exports.isValidUserBody = (data, callback) => {
    if (!data.hasOwnProperty('uid')) {
        console.error('uid is missing.');
        callback(new Error('uid is missing.'));
        return false;
    }

    return true;
};