const userService = require('../services/user');
const Chats = require('../models/chats');
const LastMessage = require('./chats');

const jwt = require("jsonwebtoken");
const key = "whatsappSecretKey";

const getFriend = async (req, res) => {
    console.log("get friend - ", req.body);
    const friend = await userService.getUserByUsername(req.body.username);
    if (!friend) {
        return res.status(404).json({ error: ['User doesn\'t exist'] });
    }
    // Get the chats model for the current user
    const chats = await Chats.findOne({ 'username': res.locals.username });
    if (!chats) {
        return res.status(404).json({ error: ['Chats not found'] });
    }

    // Check if the friend already exists in the users array
    const isFriendExists = chats.users.some(user => user.username === friend.username);
    if (isFriendExists) {
        return res.status(400).json({ error: ['Friend already exists in your contacts!'] });
    }

    if (friend.username === chats.username) {
        return res.status(400).json({ error: ['Can\'t add yourself!'] });
    }
    // Add the friend to the users array
    chats.users.push(friend);
    await chats.save();
    const message = LastMessage.getLastMessage(friend, res.locals.username);
    res.status(200).json({ 'id': friend.id, 'user': friend, 'lastMessage': message});
    res.end();
};

const isLoggedIn = async (req, res, next) => {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const token = req.headers.authorization.split(" ")[1];
        //const tokenObject = JSON.parse(token);
        //const extractedToken = tokenObject.token;
        const extractedToken = token;
        try {
            // Verify the token is valid
            const data = jwt.verify(extractedToken, key, { algorithm: 'HS256' });
            // Token validation was successful. Continue to the actual function (index)
            res.locals.username = data.username;
            return next()
        } catch (err) {
            return res.status(401).send("Invalid Token");
        }
    }
    else
        return res.status(403).send('Token required');
}

const getUserDetails = async (req, res) => {
    const user = await userService.getUserByUsername(res.locals.username);

    if (!user) {
        console.log("err - getdetails");
        return res.status(404).json({ error: ['User not found'] });
    }

    // Return the user details
    res.status(200).json(user);
    res.end();
};

module.exports = {
    getFriend,
    isLoggedIn
};