require('dotenv').config();

const PaymentController = require('./controllers/PaymentController');
const SubscriptionController = require('./controllers/SubscriptionController');
const ErrorHandler = require('./middlewares/ErrorHandler');
const connection = require('./config/Database');

const express = require('express');
const Plan = require('./domain/models/Plan');
const Subscription = require('./domain/models/Subscription');

const app = express();
app.use(express.json());

app.use('/', PaymentController);
app.use('/', SubscriptionController);
app.use(ErrorHandler);

app.listen(process.env.PORT, () => {
    console.log(`Servidor rodando na porta ${process.env.PORT}`);
});