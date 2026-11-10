var exec = require('cordova/exec');

exports.describe = function (imagePath, options, success, error) {
    var opts = options || {};
    exec(success, error, 'CactusMiragePlugin', 'describe', [imagePath, opts]);
};
