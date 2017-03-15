'use strict';

const consts = require('../common/consts');
const utils = require('../common/utils');
const Requestify = require('requestify');

const TAG = consts.PREFIX + ':update]';
// ---------------------------------------------------------------------------------------------
//     [method]             [source]           [when phase == 'pkg1.6']     [Rest API]
// ---------------------------------------------------------------------------------------------
// create sensor config     (sid, uid, did)     -> only Rest                post    /sensor
// ---------------------------------------------------------------------------------------------
// update sensor config     (sid, uid, did)     -> only Rest                put     /sensor/:sid
// delete connected device  (sid, uid, did)     -> only Rest                put     /sensor/:sid
// ---------------------------------------------------------------------------------------------
// delete sensor config     (sid, did, uid)     -> only Rest                delete  /sensor/:sid
// reset sensor config      (sid, did)          -> only Rest                delete  /sensor/:sid
// ---------------------------------------------------------------------------------------------
// delete gateway config    (did, uid)          -> both Legacy and Rest     delete  /device/:did
// reset gateway config     (did)               -> both Legacy and Rest     delete  /device/:did
// ---------------------------------------------------------------------------------------------

module.exports.update = (event, context, callback) => {
    if (typeof event.body === 'string') event.body = JSON.parse(event.body);
    utils.logEvent(TAG, event);

    const data = event.body;
    const source = data.params.source;

    let promise;
    if (data.method === 'delete_gateway_config' || data.method === 'reset_gateway_config')
        Promise.all([requestLegacy(), requestRest()])
            .then(responses => callback(null, {statusCode: 200, body: consts.OKMessage}))
            .catch(reason => callback(new Error(reason)));

    else if (data.phase === 'pkg1.6') promise = requestRest();
    else promise = requestLegacy();

    if (promise)
        promise.then(response => callback(null, {statusCode: 200, body: response}))
            .catch(reason => callback(new Error(reason)));

    function requestLegacy() {
        const URL_LEGACY = consts.URL_LEGACY_QA + '/update';

        return new Promise((resolve, reject) => {
            console.log(TAG, 'requestLegacy', URL_LEGACY, data);

            Requestify.post(URL_LEGACY, data).then(response => {
                console.log(TAG, 'legacy response:', response.body);

                const body = JSON.parse(response.body);
                if (body.result === '1') resolve(consts.OKMessage); // {result: "1"}
                else reject(body);

            }).fail(response => {
                console.log(TAG, 'legacy response:', response);
                reject(response.body);
            });
        });
    }

    function requestRest() {
        return new Promise((resolve, reject) => {
            routeRest().then(response => {
                console.log(TAG, 'rest response:', response.body);

                const body = JSON.parse(response.body);
                if (body.result === '1') resolve(consts.OKMessage);
                else reject(body);

            }).fail(response => {
                console.log(TAG, 'rest response:', response);
                reject(response.body);
            });
        });

        function routeRest() {
            const URL_REST_SENSORS = consts.URL_REST + '/sensors';
            const URL_REST_SENSORS_SID = consts.URL_REST + '/sensors/' + source['sid'];

            switch (data.method) {
                case 'create_sensor_config':
                    console.log(TAG, 'post', URL_REST_SENSORS);
                    return Requestify.post(URL_REST_SENSORS, data);

                case 'update_sensor_config':
                case 'delete_mc_connected_device':
                    console.log(TAG, 'put', URL_REST_SENSORS_SID);
                    return Requestify.put(URL_REST_SENSORS_SID, data);

                case 'delete_sensor_config':
                case 'reset_sensor_config':
                    console.log(TAG, 'delete', URL_REST_SENSORS_SID);
                    return Requestify.delete(URL_REST_SENSORS_SID);

                case 'delete_gateway_config':
                case 'reset_gateway_config':
                    const url = utils.getUrlwithPathParameter(consts.URL_REST , data, source);
                    console.log(TAG, 'delete', url);
                    return Requestify.delete(url); // body is not allowed
            }
        }
    }
};
