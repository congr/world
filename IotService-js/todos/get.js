'use strict';

const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();

module.exports.get = (event, context, callback) => {
    console.log(`todo get event ${event}`, event);
    console.log("todo get event.path", event.path);
    console.log("todo get event.queryStringParameters", event.queryStringParameters);
    console.log("todo get event.pathParameters", event.pathParameters);
    console.log("todo get event.body", event.body);

    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {
            id: event.pathParameters.id,
        },
    };

    // fetch todo from the database
    dynamoDb.get(params, (error, result) => {
        // handle potential errors
        if (error) {
            console.error(error);
            callback(new Error('Couldn\'t fetch the todo item.'));
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
