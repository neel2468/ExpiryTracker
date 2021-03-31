const {getProducts,createProduct} = require('./product.service');


module.exports = {
    
    fetchProducts:(req,res)=> {
        const userEmail = req.body.email;
        console.log(userEmail);
        getProducts(userEmail,(err,results) => {
            if(err) {
                console.log(err);
            }
            else {
               
                return res.send({"data":results});
            }
        });
    },

    createProducts:(req,res)=> {
        const id = req.body.id;
        const title = req.body.title;
        const exprd = req.body.exprd;
        const email = req.body.email;

        createProduct(id,email,title,exprd,(err,results) => {
            if(err) {
                console.log(err);
            }
            else {
               
                return res.json(results);
            }
        });

    }
}