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

router.post('/', async (req,res)=>{
    try{
        console.log(req.body);
        const {email,password} = req.body;
        database.query("SELECT * FROM signup WHERE email = ?",[email],async(error,result)=>{
             if(error){
                console.log("database query error"+error);
             }
             else{
                if(result.length > 0){
                    console.log("Query executed");
                    const hashedPassword = result[0].password;
                    const passwordMatch = await bcrypt.compare(password,hashedPassword);
                    if(passwordMatch){
                        console.log("Login Successfully");
                        res.status(200).json(
                            {
                                status : true,
                                msg : 'method allowed'
                            }
                         )
                    }
                    else{
                        console.log("user not found");
                        res.status(401).json(
                            {
                                status : false,
                                msg : 'Invalid email or password'
                            }
                         )
                    }
                    
                }
                else{
                    console.log("Invalid Credentials");
                }
             }
        });
    }
    catch(error){
        console.log("This is exception error"+error);
    }
});

module.exports = router;