'use strict';

// const ERROR_MISSING_PATH_PARAM_DID = 'missing path parameter: did';
// const ERROR_MISSING_PATH_PARAM_SID = 'missing path parameter: sid';

module.exports.logEvent = (TAG, event) => {
    //console.log(TAG, 'event', event);
    console.log(TAG, 'event.path', event['path']);
    console.log(TAG, 'event.pathParameters', event['pathParameters']);
    console.log(TAG, 'event.queryStringParameters', event['queryStringParameters']);
    console.log(TAG, 'event.body', JSON.stringify(event['body'], null, " "));
};


/*"result": {
    "settings": {
        "th": {
            "nick": "nnnnn",
                "connected_device_list": [
                {
                    "id": "id_a",
                    "type": "A"
                }
            ],
                "next_xx": "1234",
                "h_alarm": false,
                "h_max": 5099,
                "pickup_alarm": true
        }
    }
}*/
module.exports.toSettingsResult = (value) => {
    let settings = {};
    settings[value.type] = value.settings;

    let result = {settings: settings};
    return JSON.stringify({result: result});
};

/*"result": {
    "sensors": [
        {
            "model": "xx",
            "euid": "euid1",
            "updatedAt": "2017-03-13T01:13:35.283Z",
            "createdAt": "2017-03-13T00:37:22.257Z",
            "uid": "cecil1",
            "did": "did000",
            "type": "th",
            "sid": "sid000",
            "nick": "nnnnn",
            "connected_device_list": [
                {
                    "id": "id_a",
                    "type": "A"
                }
            ],
            "next_xx": "1234",
            "h_alarm": false,
            "h_max": 5099,
            "pickup_alarm": true
        }
    ]
}*/
module.exports.toSensorsResult = (values) => {
    let sensors = [];
    for (let value of values) {
        let settings = value.settings;
        delete value.settings;
        for (let key of Object.keys(settings)) value[key] = settings[key];
        sensors.push(value);
    }

    let result = {sensors: sensors};
    return JSON.stringify({result: result});
};

module.exports.getSource = (TAG, event) => {
    let queryString = event.queryStringParameters;
    if (typeof queryString === 'string') queryString = JSON.parse(queryString);

    let source = queryString;
    let key = Object.keys(event.pathParameters)[0];
    source[key] = event.pathParameters[key];
    console.log(TAG, "source", source);
    return source;
};


/*
 module.exports.isValidSensorUrl = (TAG, event, callback) => {
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
 };*/
