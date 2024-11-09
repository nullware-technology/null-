const SubscriptionService = require('../service/SubscriptionService');
const StripeService = require('../service/StripeService');

const express = require('express');
const app = express();

app.get('/subscription/:idUser', async (req, res) => {
    // Recuperar ID da sessão.
    var idUser = req.params.idUser;

    const subscription = await StripeService.findSubscriptionByUser(idUser);

    res.send(subscription);
});

app.get('/subscription/cancel/:idUser', async (req, res) => {
    var idUser = req.params.idUser;
    
    const url = await StripeService.cancelStripeSubscription(idUser);

    res.send(url);
});

module.exports = app;