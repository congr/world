'use strict';

const consts = require('../consts');
const utils = require('../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ':lookupDevice';

// need to call RC Legacy
// LookupDeviceSummary with uid or without uid
module.exports.lookupDevice = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidDeviceUrl(TAG, event, callback)) return;

    const data = JSON.parse(event.body);
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


    dynamoDb.scan(params, (error, result) => {
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t fetch the devices.'));
            return;
        }

        // create a response
        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Items),
        };
        callback(null, response);
    });
};
