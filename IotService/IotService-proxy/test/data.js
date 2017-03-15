'use strict';

const
    $did = 'didMocha1',
    $sid = 'sidMocha21',
    $uid = 'uidMocha2';

const CREATE_SENSOR = {
    "JSON-rpc": "2.0",
    "method": "create_sensor_config",
    "uuid": "uniquemessageId",
    "phase": "pkg1.6",
    "region": "kic",
    "country_code": "kr",
    "params": {
        "version": "11234",
        "mid": "messageId",
        "source": {
            "did": $did,
            "sid": $sid,
            "uid": $uid,
            "tz": "Asia/Seoul",
            "ts": ""
        },
        "settings": {
            "th": {
                "nick": "from Mocha",
                "euid": "euid1",
                "model": "ISC61"
            }
        }
    }
}

const CREATE_SENSOR_OTHER = {
    "JSON-rpc": "2.0",
    "method": "create_sensor_config",
    "uuid": "uniquemessageId",
    "phase": "pkg1.6",
    "region": "kic",
    "country_code": "kr",
    "params": {
        "version": "11234",
        "mid": "messageId",
        "source": {
            "did": $did,
            "sid": $sid,
            "uid": $uid + '-other',
            "tz": "Asia/Seoul",
            "ts": ""
        },
        "settings": {
            "th": {
                "nick": "from Mocha - other",
                "euid": "euid1",
                "model": "ISC61"
            }
        }
    }
}

const UPDATE_SENSOR = {
    "JSON-rpc": "2.0",
    "method": "update_sensor_config",
    "uuid": "uniquemessageId",
    "phase": "pkg1.6",
    "region": "kic",
    "country_code": "kr",
    "params": {
        "version": "11234",
        "mid": "messageId",
        "source": {
            "did": $did,
            "sid": $sid,
            "uid": $uid,
            "tz": "Asia/Seoul",
            "ts": "1234"
        },
        "settings": {
            "th": {
                "nick": $uid + " - What a nice emoji😀!?",
                "t_alarm": false,
                "h_alarm": false,
                "t_idx": -1,
                "t_min": 5000,
                "t_max": 6799,
                "h_idx": 5,
                "h_min": 5000,
                "h_max": 5099,
                "ac_t": 3399,
                "ac_op": true,
                "connected_id": "deprecated_field",
                "connected_device_list": [{
                    "connected_id": "conn_1",
                    "device_type": 5,
                    "temperature": 5400,
                    "humidity": 5000,
                    "device_push": false
                }, {
                    "connected_id": "conn_2",
                    "device_type": 6,
                    "humidity": 6000,
                    "device_push": false
                }]
            }
        }
    }
};

const LOOKUP_SENSOR = {
    "JSON-rpc": "2.0",
    "method": "lookup_sensor_config",
    "uuid": "uniquemessageId",
    "phase": "pkg1.6",
    "region": "kic",
    "country_code": "kr",
    "params": {
        "version": "11234",
        "mid": "messageId",
        "source": {
            "did": $did,
            "sid": $sid,
            "uid": $uid,
            "tz": "Asia/Seoul",
            "ts": "1234"
        },
        "query": {
            "type": "th"
        }
    }
}

const DELETE_SENSOR ={
    "JSON-rpc": "2.0",
    "method": "delete_sensor_config",
    "uuid": "uniquemessageId",
    "phase": "pkg1.6",
    "region": "kic",
    "country_code": "kr",
    "params": {
        "version": "11234",
        "mid": "messageId",
        "source": {
            "did": $did,
            "sid": $sid,
            "uid": $uid,
            "tz": "Asia/Seoul",
            "ts": "1234"
        },
        "settings": {
            "target": "th"
        }
    }
}

const RESET_SENSOR ={
    "JSON-rpc": "2.0",
    "method": "reset_sensor_config",
    "uuid": "uniquemessageId",
    "phase": "pkg1.6",
    "region": "kic",
    "country_code": "kr",
    "params": {
        "version": "11234",
        "mid": "messageId",
        "source": {
            "did": $did,
            "sid": $sid,
            "uid": $uid,
            "tz": "Asia/Seoul",
            "ts": "1234"
        }
    }
}

module.exports.data = {
    CREATE_SENSOR: CREATE_SENSOR,
    UPDATE_SENSOR: UPDATE_SENSOR,
    LOOKUP_SENSOR: LOOKUP_SENSOR,
    DELETE_SENSOR: DELETE_SENSOR,
    RESET_SENSOR: RESET_SENSOR,
    CREATE_SENSOR_OTHER: CREATE_SENSOR_OTHER,
};
