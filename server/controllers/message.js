const messageService = require('../services/message');
const Chats = require('../models/chats');
const User = require('../models/user');
const userService = require('../services/user');

const jwt = require("jsonwebtoken");
const key = "whatsappSecretKey";

const createMessage = async (req, res) => {
    const me = await userService.getUserByUsername(res.locals.username);
    if (!me) {
        return res.status(404).json({ error: ['Error!'] });
    }
    const messageId = req.params.id;
    const message = await messageService.CreateMessage(req.body, messageId, me);
    if (!message) {
        return res.status(404).json({ error: ['User doesn\'t exist'] });
    }
    // Get the chats model for the current user
    const myChat = await Chats.findOne({ 'username': res.locals.username });
    const friend = await User.findOne({ '_id': req.params.id });
    const friendChat = await Chats.findOne({ 'username': friend.username });

    if (!myChat || !friendChat || !friend) {
        return res.status(404).json({ error: ['Chats not found'] });
    }

    // Add the friend to the users array
    myChat.messages.push(message);
    await myChat.save();

    // Add the friend to the users array
    friendChat.messages.push(message);
    await friendChat.save();

    res.status(200).json({ "id": message.key, "created": message.created, "sender": message.sender, "content": message.content });
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

module.exports = {
    createMessage,
    isLoggedIn
};