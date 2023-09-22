const express = require('express');
const router = express.Router();
router.use(express.static('public'));

router.get('/',(req,res)=>{
    res.render('dashboard');
});
module.exports = router;