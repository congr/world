'use strict';

const utils = require('../../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = 'createDevice';

module.exports.createDevice = (event, context, callback) => {
    utils.logEvent(TAG, event);

    const data = JSON.parse(event.body);
    if (!utils.isValidDeviceBody(data)) return;

    const timestamp = new Date().toJSON();
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Item: {
            did: data.did,
            sid: data.sid,
            uid: data.uid,
            euid: data.euid,
            model: data.model,
            type: data.type,
            settings: data.settings,
            createdAt: timestamp,
            updatedAt: timestamp,
        },
    };

    // write the device to the database
    dynamoDb.put(params, (error, result) => {
        // handle potential errors
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t create the device item.'));
            return;
        }

        // create a response
        const response = {
            statusCode: 200,
            body: JSON.stringify(result.Item),
        };
        callback(null, response);
    });
};
