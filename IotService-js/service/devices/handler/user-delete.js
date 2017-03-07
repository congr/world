'use strict';

const utils = require('../../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = 'deleteUser';

// need to call RC Legacy
// unregister user
module.exports.deleteUser = (event, context, callback) => {
    utils.logEvent(TAG, event);

    if (!utils.isValidUserBody(event, callback)) return;

    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            uid: data.uid
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
