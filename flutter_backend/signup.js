const express = require('express');
const bcrypt = require('bcrypt');
const database = require('./Database/db');
const router = express.Router();
router.use(express.json());

router.get('/',(req,res)=>{
        res.status(500).json(
            {
                status : false,
                msg : 'method not allowed'
            }
        )
});


router.post('/',async (req,res)=>{
    const {name,email,password} = req.body;
    console.log(req.body);
    const saltRounds = 10;
    const hashedPassword = await bcrypt.hash(password,saltRounds);
    database.query("INSERT INTO signup (name,email,password) VALUES (?,?,?)",[name,email,hashedPassword],(error,result)=>{
         if(error){
            console.log("database query error"+error);
         }
         else{
            if(result){
                console.log("Query executed");
                res.status(200).json(
                    {
                        status : true,
                        msg : 'Method Allowed'
                    }
                )
            }
            else{
                console.log("Query did not return any result");
            }
         }
    });
});
module.exports = router;