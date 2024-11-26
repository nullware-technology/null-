const Sequelize = require("sequelize");
const connection = require("../../config/Database");
const Plan = require("./Plan");

const Subscription = connection.define('subscription', {
    id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        allowNull: false,
        primaryKey: true
    },
    start: {
        type: Sequelize.INTEGER,
        field: 'start_date',
        allowNull: false
    },
    end: {
        type: Sequelize.INTEGER,
        field: 'end_date',
        allowNull: false
    },
    status: {
        type: Sequelize.STRING,
        allowNull: false
    },
    idUser: {
        type: Sequelize.INTEGER,
        field: 'users_id',
        allowNull: false
    },
    idPlan: {
        type: Sequelize.INTEGER,
        field: 'plan_id',
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
