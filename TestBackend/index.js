const express = require('express')
const bodyParser = require('body-parser');
const connection = require('./services/mysql');

const app = express()
app.use(bodyParser.json());

app.get('/', (req, res) => {
    const { barcode } = req.body
    return connection.query(`SELECT sm.item_code, sm.item_name, sm.barcode
        FROM zc_stock_barcode sb
        LEFT JOIN zc_stock_master sm
        ON sb.item_code = sm.item_code
        WHERE sb.barcode = '${barcode}'`, (error, data) => {
        if (!!error) {
            res.status(400).send({ message: 'An error occured!' })
        } else {
            res.send({ data: data[0] })
        }
    })
});

app.listen(8000, () => console.log('Server is running on:', 'http://localhost:8000'))