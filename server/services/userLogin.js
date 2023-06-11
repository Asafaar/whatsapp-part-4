const UserLogin = require('../models/userLogin');
const User = require('../services/user');
const Chats = require('../models/chats');

const CreateUser = async (u) => {
    const user = new UserLogin({ "username": u.username, "password": u.password, "displayName": u.displayName, "profilePic": u.profilePic });
    User.createUser(u.username, u.displayName, u.profilePic);
    const chats = new Chats({ "username": u.username });
    await chats.save();
    return await user.save();
}

const getUser = async (u) => {
    const user = await UserLogin.findOne({ "username": u.username, "password": u.password });
    return user;
}

const getUserByUsername = async (username) => {
    const user = await UserLogin.findOne({ "username": username });
    return user;
}


module.exports = { CreateUser, getUser, getUserByUsername }