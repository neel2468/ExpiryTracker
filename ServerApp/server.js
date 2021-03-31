const express = require('express');
const app = express();
const cors = require('cors');
const bodyParser = require('body-parser');

const productRouter = require('./api/product.router');

app.use(express.json());
app.use(cors());
app.use(bodyParser.urlencoded({
    extended:false
}))

app.use(function (req,res,next) {
    res.setHeader('Access-Control-Allow-Origin','*');
    res.setHeader('Access-Control-Allow-Methods','GET','POST','OPTIONS');
    res.setHeader('Access-Control-Allow-Headers','X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials',true);

    next();
});

require('./config/connection')(); 
app.use('/api/v1',productRouter);


const port = 8085;
app.listen(port,() => {
    console.log("Server up and running on PORT: ",port);
});




