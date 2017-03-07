'use strict';

const utils = require('../../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = 'deleteDevice';

// need to call RC Legacy
// deleteDevice if uid is provided,
// resetDevice
module.exports.deleteDevice = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidDeviceUrl(event, callback)) return;

    let params = {};
    if (data.hasOwnProperty('uid'))
        params = {
            TableName: process.env.DYNAMODB_TABLE,
            Key: {
                did: event.pathParameters.did,
                uid: data.uid
            }
        };
    else
        params = {
            TableName: process.env.DYNAMODB_TABLE,
            Key: {
                did: event.pathParameters.did
            }
        };

    dynamoDb.delete(params, (error) => {
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t remove the todo item.'));
            return;
        }

        const response = {
            statusCode: 200,
            body: JSON.stringify({}),
        };
        callback(null, response);
    });
};
