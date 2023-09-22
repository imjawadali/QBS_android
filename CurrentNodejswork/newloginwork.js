const express = require('express');
const app = express();
const path = require('path');
const port = 9090;
app.set('view engine','ejs');

const login = require('./Routes/login');
const signup = require('./Routes/signup');
const dashboard = require('./Routes/dashboard')

app.use('/signup',signup);
app.use('/',login);
app.use('/dashboard',dashboard);


app.listen(port,()=>{
    console.log("Server is listening at 9090 port");
});
































// const express = require('express');
// const connection = require('./database_config');
// const app = express();
// const bodyParser = require('body-parser');
// const bcrypt = require('bcrypt');
// app.use(bodyParser.json());
// app.use(express.static('public'));
// app.use(bodyParser.urlencoded({extended : true}));
// app.set('view engine','ejs');





// app.get('/signup',(req,res)=>{
//     res.render('signup');
// });


// app.post('/signup', async(req,res)=>{
//     try{
//         console.log(req.body);
        
//     }
//     catch{

//     }
// });
