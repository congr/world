'use strict';

const consts = require('../common/consts');
const dbHelper = require('../common/db-helper');
const utils = require('../common/utils');
const Requestify = require('requestify');

const TAG = consts.PREFIX + ':update]';

// create sensor config
// update sensor config
module.exports.update = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;

    let promise;
    if (data.phase === 'pkg1.6')
        promise = requestRest();
    else
        promise = requestLegacy();

    promise
        .then(response => callback(null, {statusCode: 200, body: response}))
        .catch(reason => callback(new Error(reason)));

    function requestLegacy() {
        const URL_LEGACY = consts.URL_LEGACY_QA + '/update';

        return new Promise((resolve, reject) => {
            console.log(TAG, 'requestLegacy', URL_LEGACY);
            Requestify.post(URL_LEGACY, data).then(response => {
                console.log(TAG, 'legacy response:', response.body);
                const body = JSON.parse(response.body);
                if (body.result === '1') resolve(consts.OKMessage);
                else reject(body);

            }).fail(response => {
                console.log(TAG, 'legacy response:', response);
                reject(response.body);
            });
        });
    }

    function requestRest() {
        return new Promise((resolve, reject) => {
            postOrPutRest().then(response => {
                console.log(TAG, 'rest response:', response.body);
                const body = JSON.parse(response.body);
                if (body.result === '1') resolve(consts.OKMessage);
                else reject(body);

            }).fail(response => {
                console.log(TAG, 'rest response:', response);
                reject(response.body);
            });
        });

        function postOrPutRest() {
            if (data.method === 'create_sensor_config') {
                const URL_REST = consts.URL_REST + '/sensors';
                console.log(TAG, 'postRest', URL_REST);
                return Requestify.post(URL_REST, data);

            } else if (data.method === 'update_sensor_config') {
                const URL_REST = consts.URL_REST + '/sensors/' + source.sid;
                console.log(TAG, 'putRest', URL_REST);
                return Requestify.put(URL_REST, data);
            }
        }
    }
};
