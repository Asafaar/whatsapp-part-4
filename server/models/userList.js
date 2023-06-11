const mongoose = require('mongoose');
const Schema = mongoose.schema;

const UserList = new Schema({
    user : {
        type : String,
        required : true
    },
    friendUserName: {
        type: String,
        required: true
    }
    
});

module.exports = mongoose.model('UserList', UserList);