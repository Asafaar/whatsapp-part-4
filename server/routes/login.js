const express = require('express');
const  {login}  = require('../controllers/login.js');
const routerLogin = express.Router();

routerLogin.post('/', login);

module.exports = routerLogin;
