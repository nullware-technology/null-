const Subscription = require('../domain/models/Subscription');
const Plan = require('../domain/models/Plan');
const SubscriptionStatusEnum = require('../domain/enum/SubscriptionStatusEnum');
const NullPlusException = require('../domain/exception/NullPlusException');

const SUBSCRIPTION_NOT_FOUND = 'Assinatura não encontrada.';
const INVALID_PLAN = 'Plano inválido.';
const ALREADY_CANCELLED = 'Assinatura já cancelada.';
const TO_BE_CANCELLED = 'Assinatura agendada para cancelamento.';

class SubscriptionService {

    async createSubscription(subscription) {
        try {
            var selectedPlan = await Plan.findOne({
                where: { idStripe: subscription.plan.id }
            });

            if (selectedPlan == null) {
                throw new NullPlusException(INVALID_PLAN, 404);
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
            const subscription = await this.findSubscriptionByStripe(idStripeSubscription);
            subscription.status = SubscriptionStatusEnum.CANCELED;

            await subscription.save();
        } catch (error) {
            console.log(error);
        }
    }

    async scheduleCancellation(idUser) {   
        try {
            const subscription = await this.findSubscriptionByUser(idUser);

            if (subscription.status === SubscriptionStatusEnum.CANCELED) {
                throw new NullPlusException(ALREADY_CANCELLED, 400);
            }
            if (subscription.status === SubscriptionStatusEnum.TO_BE_CANCELLED) {
                throw new NullPlusException(TO_BE_CANCELLED, 409);
            }

            subscription.status = SubscriptionStatusEnum.TO_BE_CANCELLED;
            await subscription.save();

            return subscription;
        } catch (error) {
            throw error;
        }
    }

    async findSubscriptionByUser(idUser) {
        try {
            const subscription = await Subscription.findOne({
                where: { idUser: idUser }
            });

            if (!subscription) {
                throw new NullPlusException(SUBSCRIPTION_NOT_FOUND, 404);
            }

            return subscription;
        } catch (error) {
            throw error;
        }
    }

    async findSubscriptionByStripe(idStripe) {
        try {
            const subscription = await Subscription.findOne({
                where: { idStripeSubscription: idStripe }
            });

            if (!subscription) {
                throw new NullPlusException(SUBSCRIPTION_NOT_FOUND, 404);
            }

            return subscription;
        } catch (error) {
            throw error;
        }
    }

}

module.exports = new SubscriptionService();