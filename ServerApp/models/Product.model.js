const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const ProductSchema = new Schema({
    _id: {type: String},
    title: { type: String },
    description : { type: String },
    details: {type: Object }
});

module.exports.Products = mongoose.model('Products',ProductSchema,'Products');