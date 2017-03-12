'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');

const TAG = consts.PREFIX + ':deleteSensor]';

module.exports.deleteSensor = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;

    let promise;
    if (source['sid'] && source['uid'] && source['did']) { // delete sensor (only 1)
        console.log(TAG, 'delete sensor');
        promise = dbHelper.deleteWithSource(TAG, source);
    } else { // reset sensor - no uid provided
        console.log(TAG, 'reset sensor');
        promise = dbHelper.deleteBulkWithSource(TAG, source);
    }
    promise.then(() => callback(null, {statusCode: 200, body: consts.OKMessage}))
        .catch(reason => callback(new Error(reason)));
};
