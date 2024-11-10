const Subscription = require('../domain/models/Subscription');
const Plan = require('../domain/models/Plan');
const SubscriptionStatusEnum = require('../domain/enum/SubscriptionStatusEnum');
const ResourceNotFoundException = require('../domain/exception/ResourceNotFoundException');

const SUBSCRIPTION_NOT_FOUND = 'Assinatura não encontrada.';
const INVALID_PLAN = 'Plano inválido.';

class SubscriptionService {

    async createSubscription(subscription) {
        try {
            var selectedPlan = await Plan.findOne({
                where: { idStripe: subscription.plan.id }
            });

            if (selectedPlan == null) {
                throw new ResourceNotFoundException(INVALID_PLAN);
            }

            // Encontrar o usuário pelo e-mail
            // Associar o usuário e seu id da Stripe

            await Subscription.create({
                idPlan: selectedPlan.idPlan,
                idStripeSubscription: subscription.id,
                idUser: crypto.randomUUID(),
                status: SubscriptionStatusEnum.ACTIVE,
                start: new Date(subscription.current_period_start * 1000).toISOString(),
                end: new Date(subscription.current_period_end * 1000).toISOString()
            });
        } catch (error) {
            throw error;
        }
    }

    async cancelSubscription(idStripeSubscription) {
        try {
            const subscription = await Subscription.findOne({
                where: { idStripeSubscription: idStripeSubscription }
            });

            if (!subscription) {
                throw new ResourceNotFoundException(SUBSCRIPTION_NOT_FOUND);
            }

            subscription.status = SubscriptionStatusEnum.CANCELED;

            await subscription.save();
        } catch (error) {
            console.log(error);
        }
    }

    async findSubscriptionByUser(idUser) {
        try {
            const subscription = await Subscription.findOne({
                where: { idUser: idUser }
            });

            if (!subscription) {
                throw new ResourceNotFoundException(SUBSCRIPTION_NOT_FOUND);
            }

            return subscription;
        } catch (error) {
            throw error;
        }
    }

}

module.exports = new SubscriptionService();