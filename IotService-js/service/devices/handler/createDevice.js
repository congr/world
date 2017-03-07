'use strict';

const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies
const dynamoDb = new AWS.DynamoDB.DocumentClient();

module.exports.createDevice = (event, context, callback) => {
    console.log("createDevice", event.body);

    const timestamp = new Date().toJSON();
    const data = JSON.parse(event.body);

    if ((data.hasOwnProperty('deviceId') && data.hasOwnProperty('sensorId') && data.hasOwnProperty('userId')) === false) {
        console.error('deviceId or sensorId or userId is missing.');
        callback(new Error('Couldn\'t create the device item.'));
        return;
    }

    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Item: {
            deviceId: data.deviceId,
            sensorId: data.sensorId,
            userId: data.userId,
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
