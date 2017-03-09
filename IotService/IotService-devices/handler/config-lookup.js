'use strict';

const consts = require('../common/consts');
const utils = require('../common/utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ':lookupConfig]';

module.exports.lookupConfig = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidConfigUrl(TAG, event, callback)) return;

    const data = JSON.parse(event.body);
    const source = data.params.source;

    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        KeyConditionExpression: "#did_name = :did_value and #sid_name = :sid_value",
        FilterExpression: "#uid_name = :uid_value",
        ExpressionAttributeNames: {
            "#did_name": "did",
            "#sid_name": "sid",
            "#uid_name": "uid"
        },
        ExpressionAttributeValues: {
            ":did_value": source.did || event.pathParameters.did,
            ":sid_value": source.sid,
            ":uid_value": source.uid,
        }
    };

    dynamoDb.query(params, (error, result) => {
        console.log(TAG, "dynamo result", JSON.stringify(result, null, " "));
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t fetch the device item.'));
            return;
        }

        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Item),
        };
        callback(null, response);
    });
};
