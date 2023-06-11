const userController = require('../controllers/userLogin');
const express = require('express');
var router = express.Router();

router.route('/Users').post(userController.createUser);
router.route('/Tokens').post(userController.getUser);
router.route('/Users/:userid').get(userController.isLoggedIn, userController.getUserDetails);
  
module.exports = router;