'use strict';

const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();

// RC Legacy
// LookupDeviceSummary
module.exports.lookupDevice = (event, context, callback) => {
    console.log("todo get event.path", event.path);
    console.log("todo get event.queryStringParameters", event.queryStringParameters);
    console.log("todo get event.pathParameters", event.pathParameters);

    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            deviceId: event.pathParameters.id,
            userId: event.body.userId
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
