const router = require('express').Router();
const { fetchProducts, createProducts } = require('./product.controller');
const { newUser } = require('./user.controller');
const {login } = require('./user.controller');


router.post('/products',fetchProducts);
router.post('/register',newUser);
router.post('/login',login);
router.post('/newProduct',createProducts);

module.exports = router;