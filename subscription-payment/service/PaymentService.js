const PlansEnum = require('../enum/PlanEnum');

const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

class PaymentService {

    async createPaymentSession(paymentData) {
        var idPlanPrice = PlansEnum[paymentData.plan.toUpperCase()];

        if(!idPlanPrice) {
            return "Plano inválido.";
        }

        const session = await stripe.checkout.sessions.create({
            mode: 'subscription',
            customer_email: paymentData.userEmail,
            line_items: [{
              price: idPlanPrice,
              quantity: 1,
            }],
            success_url: 'http://localhost:8080/payment/success',
            cancel_url: 'http://localhost:8080/payment/cancel',
          });

          return session.url;
    }
    
}

module.exports = new PaymentService();