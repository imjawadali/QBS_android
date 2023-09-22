const express = require('express');
const app = express();
const port = 9090;

const signup_api = require('./signup');
const login_api = require('./login');

app.use('/signup',signup_api);
app.use('/login',login_api);

app.listen(9090,()=>{
      console.log(`Server is running at ${port}`);
});