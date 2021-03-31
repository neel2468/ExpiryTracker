const  { Users } = require('../models/User.model');
const  { Products } = require('../models/Product.model');
module.exports = {

     getProducts: async(userEmail,callBack) => {
       Users.find({"email":userEmail})
       .select({Items: 1})
       .exec(function(err,res) {
           if(err) {
               return callBack(null,err);
           } else {
              
               console.log(res);
               var Items = res[0].Items;
               Products.find({"_id":{$in: Items}})
               .select({"title": 1, "details.exprd": 1})
               .exec(function(err,res) {
                if(err) {
                    return callBack(null,err);
                }
                else {
                    return callBack(null,res);
                }
               })
           }
       })   
    },

    createProduct: async(id,userEmail,title,exprd,callBack) => {
        Products.create({
            "_id": id,
            "title": title,
            "details": {
                "exprd": exprd,
                "mfd": "",
                "weight": ""
            }

        }).then(() => {
            Users.find({"email":userEmail}).update({$push: {Items: id}}).exec();
            return callBack(null,{'msg': 'Product created successfully'})
        })
    }
}