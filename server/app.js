
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


const admin = require('firebase-admin');

const serviceAccount = require('./accountkey.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
var clients2 = {}; // modify to an object
const messageService = require('./services/message');

app.post('/api/tokenfirebase', (req, res) => {
  const token = req.body.token;
  const username = req.body.username;
  clients2[username] = token; // store token with username as key
  console.log(`reg token: ${token} ${username}`);
  res.status(200).send(`reg token: ${token} ${username}`);
});

app.post('/api/tokenfirebase/delete', (req, res) => {
  console.log("/api/tokenfirebase/delete");
  const username = req.body.username;
  delete clients2[username]; // delete token with username as key
  // clients2.remove(username); // store token with username as key
  // clients2[username] = token; // store token with username as key
  // console.log(`reg token: ${token} ${username}`);
  console.log(clients2);
  res.status(200).send("delete token");
});

app.post('/api/msgfirebase/:usernamefriend', async (req, res) => {
console.log("/api/msgfirebase/:usernamefriend");
  const message = await req.body;
  console.log(message.created);
  const usernamefriend = await req.params.usernamefriend;
  console.log(usernamefriend);

  // Get the user's token from the clients2 object
  const token = Object.keys(clients2).find(key => clients2[key] === usernamefriend);
  const recipientSocket = clients.find((s) => s.username === usernamefriend)

  console.log("recipientSocket"+ recipientSocket);
if(recipientSocket){//send from andriod to web
  const now = new Date();
 console.log(usernamefriend);
  console.log(message.sender);

  console.log("recipientSocket");
  const bubbleObjects = {
    id: message.id,
    created: now,
    sender: message.sender,
    content: message.content
};
recipientSocket.emit('message', bubbleObjects);
    return res.status(200).send("send from andriod to web");

}
console.log(token);
  console.log(clients2);
  console.log("token");
  console.log(token);

  // Check if token is undefined
  if (!token) {
    return res.status(400).send('Token not found for user');
  }

  // Send the message to the user's token using Firebase Cloud Messaging
  const messagePayload = {
    notification: {
      title: 'New Message',
    },
    data: {
      content: message.content,
      created: message.created,
      id: message.id,
      sender: JSON.stringify({
        username: message.sender.username,
        displayName: message.sender.displayName,
        profilePic: null,//the data to much big
      }),
    },
    token: token,
  };

  try {
    console.log(messagePayload);//send to andriod to andriod
    const response = await admin.messaging().send(messagePayload);
    console.log('Successfully sent message: andriod to andriod', response);
    res.send('Message sent successfully');
  } catch (error) {
    console.error('Error sending message:andriod to andriod', error);
    res.status(500).send('Error sending message');
  }
}
);






// Server-side code
var clients = [];

io.on('connection', (socket) => {// send to web to web
  console.log(clients.length + " clients connected");
    clients.push(socket);
    console.log('a user connected');

    // Set the client's username on the socket object
    socket.on('setUsername', (username) => {
        console.log(username+"asdf?");
      socket.username = username;
    });

    // Listen for incoming messages from the client
    socket.on('message', async (data) => {
        console.log("message: server ");

      const recipientSocket = clients.find((s) => s.username === data.selectedFriend)
      const token = Object.keys(clients2).find(key => clients2[key] === data.selectedFriend);
      console.log(token);
      if(token){//send to web to andriod
        console.log("//send to web to andriod");
        const messagePayload = {
          notification: {
            title: 'New Message',
          },
          data: {
            content: data.bubbleObjects.content,
            created: data.bubbleObjects.created,
            id: data.bubbleObjects.id,
            sender: JSON.stringify({
              username: data.bubbleObjects.sender.username,
              displayName: data.bubbleObjects.sender.displayName,
              profilePic: null,//the data to much big
            }),
          },
          token: token,
        };

        try {
          console.log(messagePayload);
          const response = await admin.messaging().send(messagePayload);
          console.log('Successfully sent message:send to web to andriod', response);
          // res.send('Message sent successfully');
        } catch (error) {
          console.error('Error sending message:send to web to andriod', error);
          // res.status(500).send('Error sending message');
        }
      }

        console.log("recipientSocket - ",data.selectedFriend);
      if (recipientSocket) {
        console.log("recipientSocket - ");
        recipientSocket.emit('message',data.bubbleObjects);
      }else{
            console.log("recipientSocket dont work- ");
      }
    });

    // Handle disconnections
    socket.on('disconnect', () => {
        clients.pop(socket);
        console.log(clients.length + " clients disconnected");
      console.log('user disconnected');
    });
  });
  server.prependListener("request", (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
 });
 server.listen(5001, () => {
    console.log('Server listening on port 5000');
  });
app.listen(process.env.PORT);

// firebsaeservice.firebaseservice();