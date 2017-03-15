'use strict';

const AWS = require('aws-sdk');
const dynamoDb = new AWS.DynamoDB.DocumentClient();

// query with combination of uid or did or sid
module.exports.queryWithSource = (TAG, source) => { // {source: {sid: "sid1", did: "did1", uid: "uid1"}
    const params = makeQueryParams(source);

    return new Promise((resolve, reject) => {
        console.log(TAG, 'dynamoDb.query', JSON.stringify(params, null, " "));

        dynamoDb.query(params, (error, result) => {
            console.log(TAG, "dynamo result", JSON.stringify(result, null, " "));

            if (error) reject(error);
            resolve(result ? result['Items']: '');
        });
    });
};

function makeQueryParams(source) {
    let conditions = {};
    if (source['sid']) keyConditions(conditions, 'sid', source.sid);
    if (source['uid']) keyConditions(conditions, 'uid', source.uid);
    if (source['did']) keyConditions(conditions, 'did', source.did);

    let params = {};
    params['TableName'] = process.env.DYNAMODB_TABLE;
    params['KeyConditions'] = conditions;

    // choose 2 keys with index
    if (source['sid'] && source['uid'] && source['did']) {
        delete conditions.did; // query with primary key
    } else if (!source['sid'] && source['uid'] && source['did']) {
        params['IndexName'] = "did-uid-index"; // GSI
    } else if (!source['sid'] && !source['uid'] && source['did']) {
        params['IndexName'] = "did-uid-index"; // GSI
    } else if (source['sid'] && !source['uid'] && source['did']) {
        params['IndexName'] = "sid-did-index"; // LSI
    } else if (!source['sid'] && source['uid'] && !source['did']) {
        params['IndexName'] = "uid-index"; // GSI
    }
    return params;
}

// generate keyConditions json
function keyConditions(conditions, key, value) {
    conditions[key] = {ComparisonOperator: "EQ", AttributeValueList: [value]};
}

// delete a single item from table
module.exports.deleteWithSource = (TAG, source) => {
    const params = {
        TableName: process.env.DYNAMODB_TABLE,
        Key: {sid: source.sid, uid: source.uid}
    };

    if (source['did']) {
        params.ConditionExpression = "did = :did";
        params.ExpressionAttributeValues = {":did": source.did};
    }

    return new Promise((resolve, reject) => {
        console.log(TAG, 'dynamoDb.delete', JSON.stringify(params, null, " "));

        dynamoDb.delete(params, (error, result) => {
            console.log(TAG, "dynamo result", JSON.stringify(result, null, " "));

            if (error) reject(error);
            resolve();
        });
    });
};

// query first to get matched sid and uid and then delete a single item with promise queue
// on deletion of dynamo table, schema key should be matched
// ** batch write with delete request has limit to 25 items - so if items to delete are over than 25? - not tested
module.exports.deleteBulkWithSource = (TAG, source) => {
    return this.queryWithSource(TAG, source)
        .then(items => {
            let queue = [];
            for (let item of items) {
                let newSource = {sid: item.sid, uid: item.uid, did: item.did};
                queue.push(this.deleteWithSource(TAG, newSource));
            }

            return Promise.all(queue);
        })
};


