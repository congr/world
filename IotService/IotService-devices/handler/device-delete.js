'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');

const TAG = consts.PREFIX + ':deleteDevice]';

// need to call RC Legacy
// deleteDevice if uid is provided,
// resetDevice
module.exports.deleteDevice = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;

    dbHelper.deleteBulkWithSource(TAG, source)
        .then(() => callback(null, {statusCode: 200, body: JSON.stringify({result: "ok"})}))
        .catch(reason => callback(new Error(reason)));
};
