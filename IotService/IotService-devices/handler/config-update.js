'use strict';

const consts = require('../common/consts');
const utils = require('../common/utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ':updateConfig]';

module.exports.updateConfig = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidConfigUrl(TAG, event, callback)) return;

    const data = JSON.parse(event.body);
    const source = data.params.source;
    const settings = data.params.settings;
    const type = Object.keys(settings)[0];
    const settingsValue = settings[type];

    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            did: event.pathParameters.id,
            sid: data.sid
        },
        ExpressionAttributeNames: {
            "#did_name": "did",
            "#sid_name": "sid",
            "#uid_name": "uid",

            '#sensor_settings': 'settings',
        },
        ExpressionAttributeValues: {
            ":did_value": source.did,
            ":sid_value": source.sid,
            ":uid_value": source.uid,

            ':settings': data['settings'],
            ':linkedDevices': data['linkedDevices'],
            ':updatedAt': new Date().toJSON(),
        },
        UpdateExpression: 'SET settings = :settings, linkedDevices = :linkedDevices',
        ReturnValues: 'ALL_NEW',
    };

    dynamoDb.update(params, (error, result) => {
        console.log(TAG, "dynamo result", JSON.stringify(result, null, " "));
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t update the device item.'));
            return;
        }

        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Attributes),
        };
        callback(null, response);
    });
};
