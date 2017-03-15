'use strict';

const consts = require('../common/consts');
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
            .then(response => callback(null, {statusCode: 200, body: JSON.stringify(response)}))
            .catch(reason => callback(new Error(reason)));

    else if (data.method === 'lookup_sensor_config') {
        let promise;
        if (data.phase === 'pkg1.6')
            promise = requestRest();
        else
            promise = requestLegacy();

        promise
            .then(response => callback(null, {statusCode: 200, body: JSON.stringify(response)}))
            .catch(reason => callback(new Error(reason)));
    }

    function integrate(a, b) { // return json + json
        const result = a.result.sensors.concat(b.result.sensors);
        return result;
    }

    function requestLegacy() {
        const URL_LEGACY = consts.URL_LEGACY_QA + '/lookup';

        return new Promise((resolve, reject) => {
            console.log(TAG, 'requestLegacy', URL_LEGACY);

            Requestify.post(URL_LEGACY, data).then(response => {
                console.log(TAG, 'legacy response:', response.body);

                if (response.getBody().result['sensors']) resolve(response.getBody()); // json
                else reject(response.body);

            }).fail(response => {
                console.log(TAG, 'legacy response:', response);
                reject(response.body); // string
            });
        });
    }

    function requestRest() {
        return new Promise((resolve, reject) => {
            getRest().then(response => {
                console.log(TAG, 'rest response:', response.body); // string
                resolve(response.getBody()); // json

            }).fail(response => {
                console.log(TAG, 'rest response:', response);
                reject(response.body);
            });
        });

        function getRest() {
            const url = utils.getUrlwithPathParameter(consts.URL_REST, data, source);
            console.log(TAG, 'getRest', url);

            return Requestify.get(url);
        }
    }
};
