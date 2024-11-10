const Sequelize = require("sequelize");
const connection = require("../../config/Database");
const Plan = require("./Plan");

const Subscription = connection.define('subscription', {
    idSubscription: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        field: 'id_subscription',
        allowNull: false,
        primaryKey: true
    },
    start: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    end: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    status: {
        type: Sequelize.STRING,
        allowNull: false
    },
    idUser: {
        type: Sequelize.UUID,
        field: 'id_user',
        allowNull: false
    },
    idPlan: {
        type: Sequelize.UUID,
        field: 'id_plan',
        allowNull: false
    },
    idStripeSubscription: {
        type: Sequelize.STRING,
        field: 'id_stripe_subscription',
        allowNull: false
    }
}, { tableName: 'subscription', timestamps: false });

Plan.hasMany(Subscription, { foreignKey: 'idPlan' });
Subscription.belongsTo(Plan, { foreignKey: 'idPlan' });

module.exports = Subscription;
