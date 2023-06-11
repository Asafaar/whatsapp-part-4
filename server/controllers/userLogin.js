const userService = require('../services/userLogin');
const jwt = require("jsonwebtoken");
const key = "whatsappSecretKey";

const createUser = async (req, res) => {
    res.status(200);
    const user = await userService.getUserByUsername(req.body.username);
    if(user) {
        return res.status(404).json({ error: ['User already exists'] });
    }
    await userService.CreateUser(req.body);
    res.end();
};

const getUser = async (req, res) => {
    res.status(200);
    const user = await userService.getUser(req.body);
    if (!user) {
        console.log("404 in login - ", req.body);
        return res.status(404).json({ error: ['User doesn\'t exist'] });
    }
    console.log("login - ", req.body);
    const payload = { "username": user.username };
    const token = jwt.sign(payload, key, { algorithm: 'HS256' });
    console.log("token - ", token, "\n");
    return res.status(200).send(token);
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
    createUser,
    getUser,
    isLoggedIn,
    getUserDetails
};