const stripe = require('stripe')(process.env.STRIPE_SECRET_KEY);

const Subscription = require('../models/Subscription');
const Plan = require('../models/Plan');
const SubscriptionStatusEnum = require('../enum/SubscriptionStatusEnum');

class SubscriptionService {

    async createSubscription(eventData) {
        try {
            const session = await stripe.checkout.sessions.retrieve(eventData.id, {
                expand: ['subscription']
            });

            var selectedPlan = await Plan.findOne({
                where: {
                    id_stripe: session.subscription.plan.id
                }
            });

            // Encontrar o usuário pelo e-mail
            // Associar o usuário e seu id da Stripe

            await Subscription.create({
                id_plan: selectedPlan.id_plan,
                id_stripe_subscription: session.subscription.id,
                id_user: crypto.randomUUID(),
                status: SubscriptionStatusEnum.ACTIVE,
                start: new Date(session.subscription.current_period_start * 1000).toISOString(),
                end: new Date(session.subscription.current_period_end * 1000).toISOString()
            });
        } catch (error) {
            console.log(error);
        }
    }

}

module.exports = new SubscriptionService();