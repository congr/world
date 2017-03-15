'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');

const TAG = consts.PREFIX + ':deleteDevice]';

// deleteDevice if uid is provided,
// resetDevice
module.exports.deleteDevice = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    dbHelper.deleteBulkWithSource(TAG, utils.getSource(TAG, event))
        .then(() => callback(null, {statusCode: 200, body: consts.OKMessage}))
        .catch(reason => callback(new Error(JSON.stringify(reason))));
};
