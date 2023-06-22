const mongoose = require('mongoose');
const userSchema = require('./user');
const messageSchema = require('./message');

const Chats = mongoose.Schema({
    username: {
        type: String,
        required: true
    },
    users: {
        type: [], 
        required: true,
        default: []
    },
    messages: {
        type: [], 
        required: true,
        default: []
    }
});

module.exports = mongoose.model('Chats', Chats);