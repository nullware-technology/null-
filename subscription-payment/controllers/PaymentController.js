const express = require('express');

const app = express();

app.post('/payment/pay', (req, res) => {
  var dadosPagamento = req.body;

  res.send(dadosPagamento);
});

app.get('/payment/success', (req, res) => {
  res.send('Pagamento realizado com sucesso!');
});

app.get('/payment/cancel', (req, res) => {
  // Lógica caso queira enviar email depois.

  res.json({ cancelUrl: 'https://nullplus.com' });
});

module.exports = app;