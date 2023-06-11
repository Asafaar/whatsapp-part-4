const userController = require('../controllers/user');
const chatController = require('../controllers/chats');
const messageController = require('../controllers/message');

const express = require('express');
var router = express.Router();

router.route('/')
    .get(chatController.isLoggedIn, chatController.getChatUsers)
    .post(userController.isLoggedIn, userController.getFriend);

router.route('/:id/Messages')
    .post(messageController.isLoggedIn, messageController.createMessage)
    .get(chatController.isLoggedIn, chatController.getChatMessages);

router.route('/:id')
    .delete(chatController.isLoggedIn, chatController.removeChat);

module.exports = router;