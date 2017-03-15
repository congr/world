'use strict';

module.exports.logEvent = (TAG, event) => {
    //console.log(TAG, 'event', event);
    console.log(TAG, 'event.path', event['path']);
    //console.log(TAG, 'event.pathParameters', event['pathParameters']);
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

// baseUrl/devices/did
module.exports.getUrlwithPathParameter = (baseUrl, data, source) => {
    let url = baseUrl;
    let query = "";

    switch (data.method) {
        case 'lookup_config':// device summary (uid or no uid)
            query = this.makeQueryString(query, 'uid', source['uid']);
            url = url + '/devices/' + source.did + query;
            break;

        case 'lookup_sensor_config':
            query = this.makeQueryString(query, 'uid', source['uid']);
            query = this.makeQueryString(query, 'did', source['did']);
            url = url + '/sensors/' + source.sid + query;
            break;

        case 'delete_gateway_config':
        case 'reset_gateway_config':
            query = this.makeQueryString(query, 'uid', source['uid']);
            url = url + '/devices/' + source.did + query;
            break;
    }

    return url;
};