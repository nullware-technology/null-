const PlansEnum = require('../enum/PlanEnum');
const Subscription = require('../models/Subscription');
const SubscriptionDTO = require('../dto/SubscriptionDTO');

const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

class StripeService {

  async createPaymentSession(paymentData) {
    var idPlanPrice = PlansEnum[paymentData.plan.toUpperCase()];

    if (!idPlanPrice) {
      return "Plano inválido.";
    }

    try {
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
    } catch (error) {
      console.log(error);
    }
  }

  async findSubscriptionByUser(idUser) {
    try {
      const subscription = await Subscription.findOne({
        where: { id_user: idUser }
      });

      if (!subscription) {
        return "Assinatura não encontrada.";
      }

      const subStripe = await stripe.subscriptions.retrieve(subscription.id_stripe_subscription, {
        expand: ['default_payment_method', 'plan.product']
      });

      const subscriptionDTO = new SubscriptionDTO(
        subStripe.plan.product.name,
        subStripe.default_payment_method.card,
        subStripe.created,
        subStripe.current_period_end,
        subStripe.plan.amount,
        subStripe.plan.interval);

      return subscriptionDTO;
    } catch (error) {
      console.log(error);
    }
  }

  async editPaymentMethod(idStripeUser) {
    try {
      const session = await stripe.billingPortal.sessions.create({
        customer: idStripeUser,
        return_url: 'http://localhost:8080',
        flow_data: {
          type: 'payment_method_update',
        }
      });
    } catch (error) {
      console.log(error);
    }

    return session.url;
  }

  async cancelStripeSubscription(idUser) {
    try {
      const subscription = await Subscription.findOne({
        where: { id_user: idUser }
      });

      await stripe.subscriptions.update(subscription.id_stripe_subscription, {
        cancel_at_period_end: true
      });
    } catch (error) {
      console.log(error);
    }
  }

  async removeOldPaymentMethod(newPaymentMethod, idStripeUser) {
    try {
      const paymentMethods = await stripe.paymentMethods.list({
        customer: idStripeUser,
        type: 'card',
      });

      paymentMethods.data.forEach(async method => {
        if (method.id !== newPaymentMethod.id) {
          await stripe.paymentMethods.detach(method.id);
        }
      });
    } catch (error) {
      console.log(error);
    }
  }

}

module.exports = new StripeService();