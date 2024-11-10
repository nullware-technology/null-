const SubscriptionService = require('../services/SubscriptionService');
const StripeService = require('../services/StripeService');

const express = require('express');
const app = express();

app.get('/subscription/:idUser', async (req, res, next) => {
    try {
        // Recuperar ID da sessão.
        var idUser = req.params.idUser;

        const subscription = await SubscriptionService.findSubscriptionByUser(idUser);
        const subscriptionDTO = await StripeService.findSubscriptionByUser(subscription.idStripeSubscription);

        res.status(200).send(subscriptionDTO);
    } catch (error) {
        next(error);
    }
});

app.get('/subscription/cancel/:idUser', async (req, res, next) => {
    try {
        // Recuperar ID da sessão.
        var idUser = req.params.idUser;

        const subscription = await SubscriptionService.findSubscriptionByUser(idUser);
        const msg = await StripeService.cancelStripeSubscription(subscription.idStripeSubscription);

        res.status(200).send(msg);
    } catch (error) {
        next(error);
    }
});

module.exports = app;