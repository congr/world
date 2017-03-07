'use strict';

const utils = require('../../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = 'deleteConfig';

module.exports.deleteConfig = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidConfigUrl(event, callback)) return;

    const data = JSON.parse(event.body);
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            id: event.pathParameters.did,
            sid: data.sid
        },
    };

    // delete the todo from the database
    dynamoDb.delete(params, (error) => {
        // handle potential errors
        if (error) {
            console.error(error);
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
