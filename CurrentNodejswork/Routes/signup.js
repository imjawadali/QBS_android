const express = require('express');
const app = express();
const bcrypt = require('bcryptjs');
const router = express.Router();
const database = require('../database_config');
const bodyParser = require('body-parser');
router.use(bodyParser.json());
router.use(express.static('public'));
router.use(bodyParser.urlencoded({extended : true}));


router.get('/', (req, res) => {
    res.render('signup');
  });


router.post('/', async(req,res)=>{
    try{
        console.log(req.body);
        const email = req.body.email;
        const password = req.body.password;
        const hash = await bcrypt.hash(password,10);
        console.log(hash);
        const confirmpassword = req.body.confirmpassword;

       database.query("INSERT INTO `login`(`email`, `password`) VALUES (?,?)",[email,hash],(error,data)=>{
        if(error) throw error;
        else if(password==confirmpassword){
            console.log("Query Executed");
            res.status(200).redirect('/');
        }
        else{
           console.log("password do not match");
           res.send("do not match");
        }
       });
        
    }
    catch{

    }
});
module.exports = router;
