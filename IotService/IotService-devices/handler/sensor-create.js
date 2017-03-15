'use strict';

const consts = require('../common/consts');
const utils = require('../common/utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies
const Requestify = require('requestify');

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ':createSensor]';

module.exports.createSensor = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;
    const settings = data.params.settings;
    const type = Object.keys(settings)[0];
    const settingsValue = settings[type];
    const euid = settingsValue.euid;
    const model = settingsValue.model;
    delete settingsValue.euid;
    delete settingsValue.model;

    // DM Thing create -> Dynamo put
    requestDM()
        .then(putDynamo)
        .then(() => {
            console.log(TAG, 'success');
            callback(null, {statusCode: 200, body: consts.OKMessage});
        })
        .catch(reason => { // string
            console.error(TAG, 'fail', reason);
            callback(new Error(reason));
        });

    function requestDM() {
        console.log(TAG, 'requestDM');
        return new Promise((resolve, reject) => {
            const reqData = {
                'deviceType': type,
                'countryCode': data.country_code,
                'deviceId': source.sid
            };
            console.log(TAG, consts.URL_DATAPOINTS, reqData);

            Requestify.post(consts.URL_DATAPOINTS, reqData).then(response => {
                console.log(TAG, 'datapoints response:', response.body);

                if (response.getBody()['result'] === '200') resolve();
                else if (response.getBody()['result'] === "Failed! Thing name aleady exist") resolve();
                else reject(response.body);

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
                    did: source.did,
                    sid: source.sid,
                    uid: source.uid,
                    euid: euid,
                    model: model,
                    type: type,
                    settings: settingsValue,
                    createdAt: timestamp,
                    updatedAt: timestamp,
                },
            };

            dynamoDb.put(params, (error, result) => {
                console.log(TAG, "dynamo result:", result);

                if (error) reject(JSON.stringify(error));
                resolve();
            });
        });
    }
};