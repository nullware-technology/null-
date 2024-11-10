const SubscriptionService = require('../services/SubscriptionService');
const StripeService = require('../services/StripeService');
const SubscriptionStatusEnum = require('../domain/enum/SubscriptionStatusEnum');

const express = require('express');
const app = express();

app.get('/subscription/:idUser', async (req, res, next) => {
    try {
        // Recuperar ID da sessão.
        var idUser = req.params.idUser;

        const subscription = await SubscriptionService.findSubscriptionByUser(idUser);
        const subscriptionDTO = await StripeService.retrieveSubscription(subscription.idStripeSubscription);

        res.status(200).send(subscriptionDTO);
    } catch (error) {
        next(error);
    }
});

app.get('/subscription/cancel/:idUser', async (req, res, next) => {
    try {
        // Recuperar ID da sessão.
        var idUser = req.params.idUser;

        const subscription = await SubscriptionService.scheduleCancellation(idUser);
        const msg = await StripeService.cancelStripeSubscription(subscription.idStripeSubscription);

        res.status(200).send(msg);
    } catch (error) {
        next(error);
    }
});

module.exports = app;