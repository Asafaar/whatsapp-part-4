const chatsService = require('../services/chats');
const jwt = require("jsonwebtoken");
const key = "whatsappSecretKey";
const Chats = require('../models/chats');
const User = require('../services/user');
const { Types } = require('mongoose');
const ObjectId = Types.ObjectId;

const isLoggedIn = async (req, res, next) => {
  // If the request has an authorization header
  if (req.headers.authorization) {
    // Extract the token from that header
    const token = req.headers.authorization.split(" ")[1];
    //const tokenObject = token;
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

const getChatUsers = async (req, res) => {
  console.log("getting chat users");
  const chat = await chatsService.getChatByUsername(res.locals.username);

  if (!chat) {
    console.log("err - getdetails");
    return res.status(404).json({ error: ['User not found'] });
  }

  const chatUsers = [];
  for (const user of chat.users) {
    const lastMessage = await getLastMessage(user, res.locals.username);
    chatUsers.push({
      id: user.id,
      user: user,
      lastMessage: lastMessage
    });
    console.log("getting user - ", user.username);

  }
  res.status(200).json(chatUsers);
  res.end();
};

const getChatMessages = async (req, res) => {
  const chat = await chatsService.getChatByUsername(res.locals.username);
  const me = await User.getUserByUsername(res.locals.username);
  const friend = await User.getUserByUserId(req.params.id);

  if (!chat || !me) {
    console.log("err - getdetails");
    return res.status(404).json({ error: ['User not found'] });
  }

  // Filter the messages based on the conditions
  const filteredMessages = chat.messages.filter(message => {
    const messageId = new ObjectId(req.params.id);
    return (messageId.equals(message.key) || ((message.key === me._id.toString() && friend.username === message.sender.username)));

  }).map(message => ({
    id: message.id,
    sender: message.sender,
    created: message.created,
    content: message.content
  }));
  // Return the filtered messages
  res.status(200).json(filteredMessages.reverse());
  res.end();
};

const getLastMessage = async (friend, myUserName) => {
  const chat = await chatsService.getChatByUsername(myUserName);
  const me = await User.getUserByUsername(myUserName);

  if (!chat || !me) {
    return null;
  }

  const filteredMessages = chat.messages.filter(message => {
    const messageId = new ObjectId(friend.id);
    return (messageId.equals(message.key) || (message.key === me._id.toString() && friend.username === message.sender.username));
  });

  const lastMessage = filteredMessages.length > 0 ? filteredMessages[filteredMessages.length - 1] : null;

  return lastMessage;
};

const removeChat = async (req, res) => {
  console.log("friend to remove - ", req.body);
  const friend = await User.getUserByUserId(req.params.id);
  const chat = await chatsService.getChatByUsername(res.locals.username);

  if (!chat || !friend) {
    console.log("err - removeChat");
    return res.status(404).json({ error: ['Chat not found'] });
  }

  // Remove objects from the users array with username equal to req.body.friendId
  await Chats.updateOne(
    { _id: chat._id },
    { $pull: { users: { username: friend.username } } }
  );
  
  // Remove messages from the chat's messages array
  chat.messages = chat.messages.filter(
    (message) =>
      message.key !== friend.id &&
      message.sender.username !== friend.username
  );
  console.log("all messages - ", chat.messages);
  console.log("freind - ", friend);


  // Save the updated chat
  await chat.save();

  // Chat successfully removed
  res.status(200).json({ message: 'Chat removed' });
  res.end();
};

module.exports = {
  isLoggedIn,
  getChatMessages,
  getChatUsers,
  removeChat,
  getLastMessage
};
