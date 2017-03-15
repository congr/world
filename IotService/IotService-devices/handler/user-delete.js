'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');

const TAG = consts.PREFIX + ':deleteUser]';

// need to call RC Legacy
// unregister user
module.exports.deleteUser = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;

    dbHelper.deleteBulkWithSource(TAG, source)
        .then(() => callback(null, {statusCode: 200, body: consts.OKMessage}))
        .catch(reason => callback(new Error(JSON.stringify(reason))));
};
