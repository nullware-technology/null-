const PlansEnum = require('../domain/enum/PlanEnum');
const SubscriptionDTO = require('../domain/dto/SubscriptionDTO');
const NullPlusException = require('../domain/exception/NullPlusException');
const StripeException = require('../domain/exception/StripeException');

const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

const INVALID_PLAN = 'Plano inválido.';
const STRIPE_SUBSCRIPTION_NOT_FOUND = 'Assinatura stripe não encontrada.';

class StripeService {

  async createPaymentSession(paymentData) {
    var idPlanPrice = PlansEnum[paymentData.plan.toUpperCase()];

    if (!idPlanPrice) {
      throw new NullPlusException(INVALID_PLAN, 404);
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
      throw error;
    }
  }

  async retrieveSubscriptionFromSession(eventDataId) {
    try{
      const session = await stripe.checkout.sessions.retrieve(eventDataId, {
        expand: ['subscription']
      });

      return session.subscription;
    } catch (error) {
      throw error;
    }
  }

  async retrieveSubscription(idStripeSubscription) {
    try {
      const subStripe = await stripe.subscriptions.retrieve(idStripeSubscription, {
        expand: ['default_payment_method', 'plan.product']
      });

      if (!subStripe) {
        throw new NullPlusException(STRIPE_SUBSCRIPTION_NOT_FOUND, 404);
      }

      const subscriptionDTO = new SubscriptionDTO(
        subStripe.plan.product.name,
        subStripe.default_payment_method.card,
        subStripe.created,
        subStripe.current_period_end,
        subStripe.plan.amount,
        subStripe.plan.interval);

      return subscriptionDTO;
    } catch (error) {
      throw new StripeException(error);
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

      return session.url;
    } catch (error) {
      throw new StripeException(error);
    }
  }

  async cancelStripeSubscription(idStripeSubscription) {
    try {
      await stripe.subscriptions.update(idStripeSubscription, {
        cancel_at_period_end: true
      });

      return "Assinatura cancelada.";
    } catch (error) {
      throw new StripeException(error);
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