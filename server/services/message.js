const newMessage = require('../models/message');

const CreateMessage = async (u, currentUser, me) => {
    const message = new newMessage({'id': '1', 'key': currentUser, 'content': u.msg, 'sender.username': me.username, 'sender.displayName': me.displayName, 'sender.profilePic': me.profilePic});
    await message.save();
    message.id = message._id;
    return await message.save();
}

const getMessageByUsername = async (username) => {
    const message = await newMessage.findOne({ "user.username": username });
    return message;
}

module.exports = { CreateMessage, getMessageByUsername }