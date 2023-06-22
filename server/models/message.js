const mongoose = require('mongoose');
const User = require('./user')

const Message = mongoose.Schema({
    key: {
        type: String,
        required: true
    }, 
    id: {
        type: String,
        required: true
    },
    content: {
        type: String,
        required: true
    },
    created: {
        type: Date,
        default: Date.now
    },
    sender: {
        username: {
            type: String,
            required: true
        },
        displayName: {
            type: String,
            required: true
        },
        profilePic: {
            type: String,
            required: true
        }
    }
});

module.exports = mongoose.model('Message', Message);