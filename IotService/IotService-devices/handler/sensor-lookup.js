'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');

const TAG = consts.PREFIX + ':lookupSensor]';

module.exports.lookupSensor = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    dbHelper.queryWithSource(TAG, utils.getSource(TAG, event))
        .then(Items => callback(null, {statusCode: 200, body: utils.toSettingsResult(Items[0])})) // return 1 sensor
        .catch(reason => callback(new Error(reason)));
};
