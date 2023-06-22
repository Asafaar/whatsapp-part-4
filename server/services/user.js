const User = require('../models/user');

const getUserByUsername = async (username) => {
    const user = await User.findOne({ "username": username });
    return user;
}

const getUserByUserId = async (id) => {
    const user = await User.findOne({ "_id": id });
    return user;
}

const createUser = async (username, displayName, profilePic) => {
    const user = new User({"id": "1",  "username": username, "displayName": displayName, "profilePic": profilePic });
    await user.save();
    user.id = user._id;
    await user.save();
}

module.exports = { getUserByUsername, getUserByUserId, createUser }