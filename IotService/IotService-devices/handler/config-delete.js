'use strict';

const consts = require('../consts');
const utils = require('../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ':deleteConfig';

module.exports.deleteConfig = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidConfigUrl(TAG, event, callback)) return;

    const data = JSON.parse(event.body);
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            id: event.pathParameters.did,
            sid: data.sid
        },
    };

    dynamoDb.delete(params, (error) => {
        if (error) {
            console.error(TAG, error);
            callback(new Error('Couldn\'t remove the todo item.'));
            return;
        }

        // create a response
        const response = {
            statusCode: 200,
            body: JSON.stringify({}),
        };
        callback(null, response);
    });
};
