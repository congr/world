'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');
const Requestify = require('requestify');

const TAG = consts.PREFIX + ':lookup]';

// lookup sensor config
// lookup config (device summary)
module.exports.lookup = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;

    let promise;
    if (data.method === 'lookup_config') { // device summary - 무조건 Legacy + Rest
        promise = requestLegacy().then(requestRest);
    } else if (data.method === 'lookup_sensor_config') {
        if (data.phase === 'pkg1.6')
            promise = requestRest();
        else
            promise = requestLegacy();
    }

    promise
        .then(response => callback(null, {statusCode: 200, body: response}))
        .catch(reason => callback(new Error(reason)));

    function requestLegacy() {
        const URL_LEGACY = consts.URL_LEGACY_QA + '/lookup';
        return new Promise((resolve, reject) => {
            console.log(TAG, 'requestLegacy', URL_LEGACY);
            Requestify.post(URL_LEGACY, data).then(response => {
                console.log(TAG, 'legacy response:', response.body);
                const body = JSON.parse(response.body);
                if (body.result !== '-1') resolve(consts.OKMessage);
                else reject(body);
            }).fail(response => {
                console.log(TAG, 'legacy response:', response);
                reject(response.body);
            });
        });
    }

    function requestRest() {
        return new Promise((resolve, reject) => {
            getRest().then(response => {
                const result = JSON.stringify(response.getBody());
                console.log(TAG, 'rest response:', result);
                resolve(result);
            }).fail(response => {
                console.log(TAG, 'rest response:', response);
                reject(response.getCode());
            });
        });

        function getRest() {
            let url = consts.URL_REST;
            let query = "";
            if (data.method === 'lookup_config') { // device summary (uid or no uid)
                query = utils.makeQueryString(query, 'uid', source['uid']);
                url = url + '/devices/' + source.did + query;
            } else if (data.method === 'lookup_sensor_config') { // sensor config
                query = utils.makeQueryString(query, 'uid', source['uid']);
                query = utils.makeQueryString(query, 'did', source['did']);
                url = url + '/sensors/' + source.sid + query;
            }
            console.log(TAG, 'getRest', url);
            return Requestify.get(url);
        }
    }
};
