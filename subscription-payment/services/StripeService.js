const PlanEnum = require('../domain/enum/PlanEnum');
const SubscriptionDTO = require('../domain/dto/SubscriptionDTO');
const NullPlusException = require('../domain/exception/NullPlusException');
const StripeException = require('../domain/exception/StripeException');

const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);
const HOST = process.env.HOST;
const PORT = process.env.PORT;

const INVALID_PLAN = 'Plano inválido.';
const SAME_PLAN = 'O plano selecionado já está ativo na assinatura.';
const STRIPE_SUBSCRIPTION_NOT_FOUND = 'Assinatura stripe não encontrada.';

class StripeService {

  async createPaymentSession(paymentData) {
    var plan = PlanEnum[paymentData.plan.toUpperCase()];

    if (!plan) {
      throw new NullPlusException(INVALID_PLAN, 404);
    }

    try {
      const session = await stripe.checkout.sessions.create({
        mode: 'subscription',
        customer_email: paymentData.userEmail,
        line_items: [{
          price: plan.priceId,
          quantity: 1,
        }],
        success_url: HOST + PORT + '/payment/success',
        cancel_url: HOST + PORT + '/payment/cancel',
      });

      return session.url;
    } catch (error) {
      throw error;
    }
  }

  async retrieveSubscriptionFromSession(eventDataId) {
    try {
      const session = await stripe.checkout.sessions.retrieve(eventDataId, {
        expand: ['subscription', 'customer']
      });

      return { subscription: session.subscription, customerEmail: session.customer.email };
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

  async updatePlan(stripeSubscriptionId, newPlan) {
    var plan = PlanEnum[newPlan.toUpperCase()];

    if (!plan) {
      throw new NullPlusException(INVALID_PLAN, 404);
    }

    try {
      const sub = await stripe.subscriptions.retrieve(stripeSubscriptionId);
      var previuousPlan = PlanEnum.getPlanById(sub.plan.id);

      if (previuousPlan.priceId === plan.priceId) {
        throw new NullPlusException(SAME_PLAN, 404);
      }

      await stripe.subscriptions.update(
        stripeSubscriptionId,
        {
          items: [
            {
              id: sub.items.data[0].id,
              price: plan.priceId,
            }
          ]
        }
      );
    } catch (error) {
      if (error instanceof NullPlusException) {
        throw error;
      }

      throw new StripeException(error);
    }
  }

  async editPaymentMethod(idStripeUser) {
    try {
      const session = await stripe.billingPortal.sessions.create({
        customer: idStripeUser,
        return_url: HOST + PORT,
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
      throw new StripeException(error);
    }
  }

}

module.exports = new StripeService();