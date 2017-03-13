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

    if (data.method === 'lookup_config')  // device summary - Legacy + Rest
        Promise.all([requestLegacy(), requestRest()])
            .then((responses) => { // json
                return integrate(responses[0], responses[1]);
            })
            .then(response => callback(null, {statusCode: 200, body: response}))
            .catch(reason => callback(new Error(reason)));

    else if (data.method === 'lookup_sensor_config') {
        let promise;
        if (data.phase === 'pkg1.6')
            promise = requestRest();
        else
            promise = requestLegacy();

        promise
            .then(response => callback(null, {statusCode: 200, body: response}))
            .catch(reason => callback(new Error(reason)));
    }

    function integrate(a, b) { // string(json + json)
        const result = a.result.sensors.concat(b.result.sensors);
        return JSON.stringify(result);
    }

    function requestLegacy() {
        const URL_LEGACY = consts.URL_LEGACY_QA + '/lookup';

        return new Promise((resolve, reject) => {
            console.log(TAG, 'requestLegacy', URL_LEGACY);
            Requestify.post(URL_LEGACY, data).then(response => {
                console.log(TAG, 'legacy response:', JSON.stringify(response.getBody()));

                if (response.getBody().result['sensors']) resolve(response.getBody()); // json
                else reject(response.getBody());

            }).fail(response => {
                console.log(TAG, 'legacy response:', response);
                reject(response.getBody());
            });
        });
    }

    function requestRest() {
        return new Promise((resolve, reject) => {
            getRest().then(response => {
                console.log(TAG, 'rest response:', JSON.stringify(response.getBody()));
                resolve(response.getBody()); // json

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
