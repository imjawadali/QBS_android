// const express = require('express');
// const reqFilter = require('./middleware');
// const path = require('path');
// const app = express();
// const mysql = require('mysql');
// const route = express.Router();
// const bodyParser = require('body-parser');
// app.set('view engine','ejs');
// // app.use(reqFilter);
// app.use(bodyParser.urlencoded({ extended: true }));
// app.use(bodyParser.json());
// route.use(reqFilter);
// app.use(express.static('public'));
// app.use(express.json());

const { route } = require("./Routes/login");


// const connection = mysql.createConnection({
//    host: 'localhost',
//    user: 'root',
//    password: '',
//    database: 'mydatabase'
// });

// connection.connect((error)=>{
//    if(error){
//       console.log("Failed");
//    }
//    else{
//       console.log("Connected");
//    }
// });




// app.get('/',(req,res)=>{
//    res.render('home');
// });
// app.get('/my', (req, res) => {
//    console.log(req.body);
//    // const username = req.body.username;
//    // const password = req.body.password;
//    // connection.query("INSERT INTO `loginform`(`username`, `password`) VALUES (?,?)", [username, password], (err, result, field) => {
//    //    if (err) throw err;
//    //    console.log("Data inserted");
//    // });
//    res.render('loginform');
// });


// app.post("/",(req,res)=>{
//      const data = req.body;
//      connection.query('INSERT INTO loginform SET ?',data,(error,result,fields)=>{
//       if(error) throw error;

//      });
// });

// app.put("/",(req,res)=>{
//    const data = ["basis","100",3];
//    connection.query("UPDATE loginform SET username = ?, password = ? where id = ?",data,(error,result,field)=>{
//      if(error) throw error;
//      res.send(result);
//    })
//    res.send("api not working");

// });
// app.get('/signup',(req,res)=>{
//     res.render('signup');
// });

// route.get('/about',(req,res)=>{
//     res.render('about');
//  });

//  route.get('/contact',(req,res)=>{
//     res.render('contact');

//  });
//  app.get('*',(req,res)=>{
//     res.send("Sorry response not found");
//  })
 
//  app.use('/',route);

// app.listen(8080,()=>{
// console.log("Server is listening at port#8080");
// });




