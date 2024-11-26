const Subscription = require('../domain/models/Subscription');
const Plan = require('../domain/models/Plan');
const SubscriptionStatusEnum = require('../domain/enum/SubscriptionStatusEnum');
const NullPlusException = require('../domain/exception/NullPlusException');
const PlanEnum = require('../domain/enum/PlanEnum');

const SUBSCRIPTION_NOT_FOUND = 'Assinatura não encontrada.';
const INVALID_PLAN = 'Plano inválido.';
const ALREADY_CANCELLED = 'Assinatura já cancelada.';
const TO_BE_CANCELED = 'Assinatura agendada para cancelamento.';

class SubscriptionService {

    async createSubscription(subscription) {
        try {
            var selectedPlan = await this.findPlanByStripeId(subscription.plan.id);
            console.log(selectedPlan);

            // Encontrar o usuário pelo e-mail
            // Associar o usuário e seu id da Stripe

            await Subscription.create({
                idPlan: Number(selectedPlan.id),
                idStripeSubscription: subscription.id,
                idUser: 1,
                status: SubscriptionStatusEnum.ACTIVE,
                start: new Date(subscription.current_period_start * 1000).toISOString(),
                end: new Date(subscription.current_period_end * 1000).toISOString()
            });
        } catch (error) {
            throw error;
        }
    }

    async updatePlan(idStripeSubscription, newPlanStripeId) {
        try {
            var selectedPlan = await this.findPlanByStripeId(newPlanStripeId);
            var subscription = await this.findSubscriptionByStripe(idStripeSubscription);
    
            subscription.idPlan = selectedPlan.id;
            subscription.save();
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
            throw error;
        }
    }

    async scheduleCancellation(idUser) {
        try {
            const subscription = await this.findSubscriptionByUser(idUser);

            if (subscription.status === SubscriptionStatusEnum.CANCELED) {
                throw new NullPlusException(ALREADY_CANCELLED, 400);
            }
            if (subscription.status === SubscriptionStatusEnum.TO_BE_CANCELED) {
                throw new NullPlusException(TO_BE_CANCELED, 409);
            }

            subscription.status = SubscriptionStatusEnum.TO_BE_CANCELED;
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

    async findPlanByStripeId(idPlanPrice) {
        try {
            var plan = await Plan.findOne({
                where: { idStripe: idPlanPrice }
            });
    
            if (plan == null) {
                throw new NullPlusException(INVALID_PLAN, 404);
            }
    
            return plan;
        } catch (error) {
            throw error;
        }
    }

}

module.exports = new SubscriptionService();