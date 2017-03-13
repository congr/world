'use strict';

// const ERROR_MISSING_PATH_PARAM_DID = 'missing path parameter: did';
// const ERROR_MISSING_PATH_PARAM_SID = 'missing path parameter: sid';

module.exports.logEvent = (TAG, event) => {
    //console.log(TAG, 'event', event);
    console.log(TAG, 'event.path', event['path']);
    console.log(TAG, 'event.pathParameters', event['pathParameters']);
    console.log(TAG, 'event.body', JSON.stringify(event['body'], null, " "));
};

module.exports.makeQueryString = (target, key, value) => {
    console.log("makeQueryString", target, key, value);
    if (!value) return ""; // if value is null, no need to make query string

    let result = "";
    if (!target) result = '?'; // now make query string

    if (result !== '?') result = target + '&';
    result = result + key + '=' + value;
    console.log("makeQueryString result", result);
    return result;
};
