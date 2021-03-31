const {createUser, getUser} = require('./user.service');



module.exports = {
    
    newUser:(req,res)=> {
        const user_name = req.body.user_name;
        const email = req.body.email;
        const password = req.body.password;
        createUser(user_name,email,password,(err,results) => {
            if(err) {
                console.log(err);
            }
            else {
               
                return res.json(results);
            }
        });
    },

    login:(req,res) => {
        const email = req.body.email;
        const password = req.body.password;
        getUser(email,password,(err,results) => {
            if(err) {
                console.log(err);
            }
            else {
               
                return res.json(results);
            }
        });
    }
}