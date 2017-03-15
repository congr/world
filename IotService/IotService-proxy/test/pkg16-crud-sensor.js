'use strict';

var chakram = require('chakram'),
    expect = chakram.expect;
const consts = require('../common/consts');
const utils = require('../common/utils');
const {data} = require('./data');
const Requestify = require('requestify');
var should = require('should');

describe("** [pkg1.6] CRUD sensor **", function () {
    const URL_PROXY_UPDATE = "https://ww000umasa.execute-api.ap-northeast-1.amazonaws.com/qa/update";
    const URL_PROXY_LOOKUP = "https://ww000umasa.execute-api.ap-northeast-1.amazonaws.com/qa/lookup";

    describe("Chakram", function () {
        it("testing framework is ok?", function () {

            const response = chakram.get("http://google.com");
            expect(response).to.have.status(200);
            expect(response).not.to.have.header('non-existing-header');
            return chakram.wait();
        });
    });

    // describe("create-sensor-config", function () {
    //     let response;
    //     before(function () {
    //         response = chakram.post(URL_PROXY_UPDATE, data.CREATE_SENSOR);
    //         return chakram.wait();
    //     });
    //
    //     it("should return 200 on success", function () {
    //         return expect(response).to.have.status(200);
    //     });
    //
    //     it("should specify '{result : 1}' in the response body", function () {
    //         return expect(response).to.have.json('result', '1');
    //     });
    // });
    //
    // describe("update-sensor-config", function () {
    //     let response;
    //     it('update sensor',function () {
    //         response= chakram.post(URL_PROXY_UPDATE, data.UPDATE_SENSOR)
    //         return chakram.wait();
    //     });
    //
    //     it("should return 200 on success", function () {
    //         return expect(response).to.have.status(200);
    //     });
    //
    //     it("should specify '{result : 1}' in the response body", function () {
    //         return expect(response).to.have.json('result', '1');
    //     });
    // });
    //
    // describe("lookup-sensor-config", function () {
    //     let response;
    //     before(function () {
    //         response = chakram.post(URL_PROXY_LOOKUP, data.LOOKUP_SENSOR);
    //         return chakram.wait();
    //     });
    //
    //     it("should return 200 on success", function () {
    //         return expect(response).to.have.status(200);
    //     });
    //
    //     it("should specify '{result : {settings: .. }}' in the response body", function () {
    //         return expect(response).to.have.schema('result', {
    //             "required": [
    //                 "settings"
    //             ]
    //         });
    //     });
    //
    //     it("should specify the updated setting params in the response body", function () {
    //         return expect(response).to.have.json('result.settings', data.UPDATE_SENSOR.params.settings);
    //     });
    // });
    //
    // describe("delete-sensor-config", function () {
    //     let response;
    //     before(function () {
    //         response = chakram.post(URL_PROXY_UPDATE, data.DELETE_SENSOR);
    //         return chakram.wait();
    //     });
    //
    //     it("should return 200 on success", function () {
    //         return expect(response).to.have.status(200);
    //     });
    //
    //     it("should specify '{result : 1}' in the response body", function () {
    //         return expect(response).to.have.json('result', '1');
    //     });
    //
    //     it('lookup-sensor-config : should have no sensor ',function () {
    //         response = chakram.post(URL_PROXY_LOOKUP, data.LOOKUP_SENSOR)
    //             .then(res => expect(res).not.to.have.status(200));
    //     });
    // });

    describe("reset-sensor-config", function () {
        it("condition 1] create-sensor-config", done => {
            chakram.post(URL_PROXY_UPDATE, data.CREATE_SENSOR).then((response) => {
                expect(response).to.have.status(200);
                expect(response).to.have.json('result', '1');
                done();
            });
        });

        it("condition 2] create-sensor-config with other uid", done => {
            chakram.post(URL_PROXY_UPDATE, data.CREATE_SENSOR_OTHER).then((response) => {
                expect(response).to.have.status(200);
                expect(response).to.have.json('result', '1');
                done();
            });
        });

        it("reset-sensor-config", done => {
            chakram.post(URL_PROXY_UPDATE, data.RESET_SENSOR).then((response) => {
                expect(response).to.have.status(200);
                expect(response).to.have.json('result', '1');
                done();
            });
        });

        it("lookup-sensor-config should not have 200", done => {
            chakram.post(URL_PROXY_LOOKUP, data.LOOKUP_SENSOR).then((response) => {
                expect(response).to.not.have.status(200);
                done();
            });
        });
    });


    // describe("xxanonymous thing name", function () {
    //
    //     var generatedThingName, anonymousDweetPost;
    //
    //     before(function () {
    //         anonymousDweetPost = chakram.post("https://dweet.io/dweet", "");
    //         return anonymousDweetPost.then(function (respObj) {
    //             generatedThingName = respObj.body.with.thing;
    //         });
    //     });
    //
    //     it("should succeed without a specified thing name, generating a random dweet thing name", function () {
    //         expect(anonymousDweetPost).to.have.status(200);
    //         expect(anonymousDweetPost).to.have.json('this', 'succeeded');
    //         return chakram.wait();
    //     });
    //
    //     it("should allow data retrieval using the generated thing name", function () {
    //         var data = chakram.get("https://dweet.io/get/latest/dweet/for/" + generatedThingName);
    //         return expect(data).to.have.json('with', function (dweetArray) {
    //             expect(dweetArray).to.have.length(1);
    //             var dweet = dweetArray[0];
    //             //expect(dweet.content).to.deep.equal(initialData);
    //             expect(dweet.thing).to.equal(generatedThingName);
    //         });
    //     });
    // });


});
