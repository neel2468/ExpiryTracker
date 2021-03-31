const {Users} = require('../models/User.model');

module.exports = {
    createUser: (user_name,email,password,callBack) => {
        Users.create({
            user_name: user_name,
            email: email,
            Password: password,
            Items: [],
        }).then(() => {
            return callBack(null,{'msg': 'User created successfully'})
        })
    }, 

    getUser: (email,password,callBack) => {
        Users.find({'email': email,'Password': password})
        .select({'email':1,Items:1})
        .exec(function(err,res) {
            if(err) {
                return callBack(null,err)
            } 
            else {
                return callBack(null,res);
            }
        })
    }
}