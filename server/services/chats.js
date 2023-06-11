const Chats = require('../models/chats');

const getChatByUsername = async (username) => {
    const chat = await Chats.findOne({ "username": username });
    return chat;
}

const removeChatByUsername = async (username) => {
    const chat = await Chats.findOneAndRemove({ "username": username });
    return chat;
}

module.exports = {
    getChatByUsername,
    removeChatByUsername
};
