'use strict';

const consts = require('../consts');
const utils = require('../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies
// AWS.config.setPromisesDependency(require('unirest'));
// const Promise = require("bluebird");
const Unirest = require('unirest');

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ':createDevice';

module.exports.createDevice = (event, context, callback) => {
    utils.logEvent(TAG, event);

    const data = JSON.parse(event.body);
    if (!utils.isValidDeviceBody(TAG, data, callback)) return;

    // DM Thing create -> Dynamo put
    requestDM()
        .then(putDynamo)
        .then(response => {
            console.log(TAG, response);
            callback(null, response)
        })
        .catch(error => {
            console.error(TAG, error);
            callback(new Error(error))
        });

    function requestDM() {
        return new Promise((resolve, reject) => {
            if (!data['type'] || !data['country_code']) throw 'type or country_code is missing';
            Unirest.post(consts.URL_DATAPOINTS)
                .headers({'Content-Type': 'application/json'})
                .send({'deviceType': data.type, 'countryCode': data.country_code, 'deviceId': data.sid})
                .end(response => {
                    console.log(TAG, 'datapoints response:', JSON.stringify(response));
                    if (response.error) reject(response.error);
                    else resolve(response);
                });
        });
    }

    function putDynamo() {
        return new Promise((resolve, reject) => {
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

            dynamoDb.put(params, (error, result) => {
                if (error) reject(error.stack);

                resolve({
                    statusCode: 200,
                    body: JSON.stringify('ok'),
                });
            });
        });
    }
};