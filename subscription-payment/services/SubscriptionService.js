const Subscription = require('../domain/models/Subscription');
const Plan = require('../domain/models/Plan');
const SubscriptionStatusEnum = require('../domain/enum/SubscriptionStatusEnum');
const NullPlusException = require('../domain/exception/NullPlusException');
const PlanEnum = require('../domain/enum/PlanEnum');

const connection = require('../config/Database');
const { QueryTypes } = require('sequelize');

const SUBSCRIPTION_NOT_FOUND = 'Assinatura não encontrada.';
const INVALID_PLAN = 'Plano inválido.';
const ALREADY_CANCELLED = 'Assinatura já cancelada.';
const TO_BE_CANCELED = 'Assinatura agendada para cancelamento.';

class SubscriptionService {

    async createSubscription(data) {
        try {
            var selectedPlan = await this.findPlanByStripeId(data.subscription.plan.id);

            const user = await connection.query('SELECT * FROM users WHERE email = :email',
                {
                    replacements: { email: data.customerEmail },
                    type: QueryTypes.SELECT
                }
            );

            await Subscription.create({
                idPlan: Number(selectedPlan.id),
                idStripeSubscription: data.subscription.id,
                idUser: user[0].id,
                status: SubscriptionStatusEnum.ACTIVE,
                start: new Date(data.subscription.current_period_start * 1000).toISOString(),
                end: new Date(data.subscription.current_period_end * 1000).toISOString()
            });

            await connection.query('UPDATE users SET plan_id = :planId WHERE id = :id',
                {
                    replacements: { planId: selectedPlan.id, id: user[0].id },
                    type: QueryTypes.SELECT
                }
            );
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