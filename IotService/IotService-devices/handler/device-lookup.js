'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');

const TAG = consts.PREFIX + ':lookupDevice]';

// need to call RC Legacy
// LookupDeviceSummary with uid or without uid
module.exports.lookupDevice = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    // const source = data.params.source;
    //const source = event.querystring;

    dbHelper.queryWithSource(TAG, utils.getSource(TAG, event))
        .then(Items => callback(null, {statusCode: 200, body: utils.toSensorsResult(Items)}))
        .catch(reason => callback(new Error(reason)));
};