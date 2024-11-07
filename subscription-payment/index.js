require('dotenv').config();

const PaymentController = require('./controllers/PaymentController');
const connection = require('./config/Database');

const express = require('express');
const Plan = require('./models/Plan');
const Subscription = require('./models/Subscription');

const app = express();

app.use('/', PaymentController);

app.listen(process.env.PORT, () => {
    console.log(`Servidor rodando na porta ${process.env.PORT}`);
});