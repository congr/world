'use strict';

const utils = require('../../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = 'updateConfig';

module.exports.updateConfig = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidConfigUrl(event, callback)) return;

    const timestamp = new Date().toJSON();
    const data = JSON.parse(event.body);

    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            did: event.pathParameters.id,
            sid: data.sid
        },
        // ExpressionAttributeNames: {
        //     '#sensor_settings': 'settings',
        // },
        ExpressionAttributeValues: {
            ':settings': data['settings'],
            ':linkedDevices': data['linkedDevices'],
            ':updatedAt': timestamp,
        },
        UpdateExpression: 'SET settings = :settings, linkedDevices = :linkedDevices, updatedAt = :updatedAt',
        ReturnValues: 'ALL_NEW',
    };

    dynamoDb.update(params, (error, result) => {
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t update the device item.'));
            return;
        }

        // create a response
        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Attributes),
        };
        callback(null, response);
    });
};
