'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');
const AWS = require('aws-sdk'); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TAG = consts.PREFIX + ":updateSensor]";

module.exports.updateSensor = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;
    const settings = data.params.settings;
    const type = Object.keys(settings)[0];

    dbHelper.queryWithSource(TAG, source) // query old settings
        .then(result => {
            const orgSettings = result[0].settings;
            patchSettings(orgSettings, settings[type]);
            return updateDynamo(orgSettings);
        })
        .then(result => {
            callback(null, {statusCode: 200, body: consts.OKMessage});
        })
        .catch(reason => {
            console.error(reason);
            callback(new Error(reason));
        });

    function updateDynamo(settingsValue) {
        console.log("settingsvalue:", settingsValue);
        const params = {
            TableName: process.env.DYNAMODB_TABLE,
            Key: {
                'sid': source.sid,
                'uid': source.uid
            },
            ConditionExpression: "did = :did",
            UpdateExpression: "SET settings = :settings, updatedAt = :updatedAt",
            ExpressionAttributeValues: {
                ":did": source.did,
                ":settings": settingsValue,
                ":updatedAt": new Date().toJSON()
            },
            ReturnValues: 'ALL_NEW',
        };

        return new Promise((resolve, reject) => {
            console.log(TAG, 'dynamoDb.update', JSON.stringify(params, null, " "));
            dynamoDb.update(params, (error, result) => {
                console.log(TAG, "dynamo result", JSON.stringify(result, null, " "));
                if (error) reject(error);
                resolve(result.Attributes);
            });
        });
    }

    function patchSettings(old, hot) {
        for (let key of Object.keys(hot)) old[key] = hot[key];
        return old; // updated settings
    }
};
