'use strict';

const consts = require('../consts');
const utils = require('../utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies
const Requestify = require('requestify');

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
            console.log(TAG, 'success', response);
            callback(null, response);
        })
        .catch(reason => {
            console.error(TAG, 'fail', reason);
            callback(new Error(JSON.stringify(reason)));
        });

    function requestDM() {
        console.log(TAG, 'requestDM');
        return new Promise((resolve, reject) => {
            Requestify.post(consts.URL_DATAPOINTS, {
                'deviceType': data.type,
                'countryCode': data.country_code,
                'deviceId': data.sid
            }).then(response => {
                console.log(TAG, 'datapoints response:', response);
                const body = JSON.parse(response.body);
                if (body.result === '200') resolve();
                else reject(body);
            }).fail(response => {
                console.log(TAG, 'datapoints response:', response);
                reject(response.body);
            });
        });
    }

    function putDynamo() {
        console.log(TAG, 'putDynamo');
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
                if (error) reject(error);

                resolve({
                    statusCode: 200,
                    body: JSON.stringify('ok'),
                });
            });
        });
    }
};