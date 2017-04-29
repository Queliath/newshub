var webpack = require("webpack");
var path = require('path');

module.exports = {
    entry: './app/app.module.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'build')
    },
    plugins: [
        new webpack.DefinePlugin({
            NEWSHUB_REST_SERVER_URI: JSON.stringify("http://localhost:8081/rest")
        })
    ],
    module: {
        loaders: [
            {
                test: /\.html?$/,
                loader: 'html-loader'
            }
        ]
    }
};