var path = require('path');
var nodeExternals = require('webpack-node-externals');
var UglifyJsPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
    entry: {
        'handler/update': './handler/update.js',
        'handler/lookup': './handler/lookup.js',
        'handler/state': './handler/state.js'
    },
    target: 'node',
    devtool: "source-map",
    output: {
        libraryTarget: 'commonjs',
        path: path.join(__dirname, '.webpack'),
        filename: '[name].js'
    },
    externals: [nodeExternals()],
    plugins: [new UglifyJsPlugin({
        mangle: {
            except: ['$super', '$', 'exports', 'require']
        },
        compress: true,
        beautify: false
    })]
};
