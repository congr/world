var path = require('path');
var nodeExternals = require('webpack-node-externals');
var UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
    entry: {
        'handler/device-delete': './handler/device-delete.js',
        'handler/device-lookup': './handler/device-lookup.js',
        'handler/sensor-create': './handler/sensor-create.js',
        'handler/sensor-delete': './handler/sensor-delete.js',
        'handler/sensor-lookup': './handler/sensor-lookup.js',
        'handler/sensor-update': './handler/sensor-update.js',
        'handler/user-delete': './handler/user-delete.js'
    },
    target: 'node',
    devtool: "source-map",
    output: {
        libraryTarget: 'commonjs',
        path: path.join(__dirname, '.webpack'),
        filename: '[name].js'
    },
    externals: [nodeExternals()],
    plugins: [ new UglifyJsPlugin({
        mangle: {
            except: ['$super', '$', 'exports', 'require']
        },
        compress: true,
        beautify: false
    })]
};
