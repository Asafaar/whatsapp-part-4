const mongoose = require('mongoose');

const UserLogin = mongoose.Schema({
    username : {
        type : String,
        required : true
    },
    password: {
        type: String,
        required : true
    },
    displayName: {
        type : String,
        required : true
    },
    profilePic : {
        type : String,
        required : true
    }
});

module.exports = mongoose.model('UserLogin', UserLogin);