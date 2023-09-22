const { Router } = require('express');
const mysql = require('mysql');
const connection = mysql.createConnection({
     host : 'localhost',
     user : 'root',
     password : '',
     database : 'flutterwork'
});
connection.connect(error=>{
     if(error){
        console.log("Database connection error"+error);
     }
     else{
        console.log("Successfully connected to database");
     }
});

module.exports = connection;