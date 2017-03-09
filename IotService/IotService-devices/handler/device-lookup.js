'use strict';

const consts = require('../common/consts');
const utils = require('../common/utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ':lookupDevice';

// need to call RC Legacy
// LookupDeviceSummary with uid or without uid
module.exports.lookupDevice = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidDeviceUrl(TAG, event, callback)) return;

    const data = JSON.parse(event.body);
    const source = data.params.source;

    let params = {};
    if (data.hasOwnProperty('uid'))
        params = {
            TableName: process.env.DYNAMODB_TABLE,
            IndexName: "did-uid-index",
            KeyConditionExpression: "#did_name = :did_value and #uid_name = :uid_value",
            ExpressionAttributeNames: {
                "#did_name": "did" || event.pathParameters.did,
                "#uid_name": "uid"
            },
            ExpressionAttributeValues: {
                ":did_value": source.did || event.pathParameters.did,
                ":uid_value": source.uid
            }
        };
    else
        params = {
            TableName: process.env.DYNAMODB_TABLE,
            KeyConditionExpression: "#did_name = :did_value",
            ExpressionAttributeNames: {
                "#did_name": "did"
            },
            ExpressionAttributeValues: {
                ":did_value": source.did || event.pathParameters.did
            }
        };

    dynamoDb.query(params, (error, result) => {
        console.log(TAG, "dynamo result", JSON.stringify(result, null, " "));
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t fetch the devices.'));
            return;
        }

        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Items),
        };
        callback(null, response);
    });
};