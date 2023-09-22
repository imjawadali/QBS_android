const express = require('express');
const app = express();
const bcrypt = require('bcryptjs');
const database = require('../database_config');
const router = express.Router();
const bodyParser = require('body-parser');
router.use(bodyParser.json());
router.use(express.static('public'));
router.use(bodyParser.urlencoded({ extended: true }));


router.get('/', (req, res) => {
  res.render('loginform');
});

router.post('/', async (req, res) => {
  try {
    const email = req.body.email;
    const password = req.body.password;

    database.query('SELECT * FROM `login` WHERE `email` = ?', [email], async (error, results) => {
      if (error) {
        throw error;
      }
      else {
            if (results.length > 0) {
              console.log(password);
              const hash = await bcrypt.hash(password,10);
              console.log(hash);

              console.log(results[0].password);

              bcrypt.compare(password, results[0].password, async function (err, isMatch) {
                
                if (isMatch) {
                  
                  console.log('Login Sucessfully');
                  res.status(200).redirect('/dashboard');
                }
                else{
                  res.send("Invalid email or password");
                }

              });
            }
            else{
              res.status(404).send("No user found!");
            }
      }
    });
  }
  catch (error) {
    console.log(error);
  }
});
module.exports = router;