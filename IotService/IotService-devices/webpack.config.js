var path = require('path');
var nodeExternals = require('webpack-node-externals');
var UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
    entry: {
        'handler/config-delete': './handler/config-delete.js',
        'handler/config-lookup': './handler/config-lookup.js',
        'handler/config-update': './handler/config-update.js',
        'handler/device-create': './handler/device-create.js',
        'handler/device-delete': './handler/device-delete.js',
        'handler/device-lookup': './handler/device-lookup.js',
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
        compress: false,
        beautify: true
    })]
};
