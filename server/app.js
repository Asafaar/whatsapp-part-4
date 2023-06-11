const express = require('express');
var app = express();
var app2 = express();
const http = require('http');
const server = http.createServer(app2);
const io = require('socket.io')(server);
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended : true}));

const cors = require('cors');
app.use(cors());
app2.use(cors());


const customENV = require('custom-env');
customENV.env(process.env.NODE_ENV, './config');
console.log(process.env.CONNECTION_STRING)
console.log(process.env.PORT)

const mongoose = require('mongoose');
mongoose.connect(process.env.CONNECTION_STRING, {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

app.use(express.static('public'))
app.use(express.json());

const users = require('./routes/userLogin');
app.use('/api', users);

const friends = require('./routes/user');
app.use('/api/Chats', friends);
// Server-side code
var clients = [];

io.on('connection', (socket) => {
    clients.push(socket);
  
    // Set the client's username on the socket object
    socket.on('setUsername', (username) => {
      socket.username = username;
    });
  
    // Listen for incoming messages from the client
    socket.on('message', (data) => {

      const recipientSocket = clients.find((s) => s.username === data.selectedFriend)

      if (recipientSocket) {
        recipientSocket.emit('message',data.bubbleObjects);
      }else{
      }
    });
  
    // Handle disconnections
    socket.on('disconnect', () => {
        clients.pop(socket);

    });
  });
  server.prependListener("request", (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
 });
 server.listen(5001, () => {
  });

app.listen(process.env.PORT);