const SubscriptionService = require('../service/SubscriptionService');

const express = require('express');
const app = express();

app.get('/subscription/:idStripeUser', async (req, res) => {
    // Recuperar ID da sessão.
    var idUser = ""

    // Recuperar id da stripe pelo id do usuário (temporariamente como parametro na rota).
    var idStripeUser = req.params.idStripeUser;

    const url = await SubscriptionService.editSubscription(idStripeUser);

    res.send(url)
});

module.exports = app;