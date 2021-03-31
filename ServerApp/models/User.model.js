const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const UserSchema = new Schema({
    user_name: { type: String },
    email: { type: String },
    Password: { type: String },
    Items: { type: Array }
});


module.exports.Users =  mongoose.model('Users',UserSchema,'Users');

