const Sequelize = require("sequelize");
const connection = require("../config/Database");
const Plan = require("./Plan");

const Subscription = connection.define('subscription', {
    id_subscription: {
        type: Sequelize.UUID,
        allowNull: false,
        defaultValue: Sequelize.UUIDV4,
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
    id_user: {
        type: Sequelize.UUID,
        allowNull: false
    },
    id_plan: {
        type: Sequelize.UUID,
        allowNull: false
    },
    id_stripe_subscription: {
        type: Sequelize.STRING,
        allowNull: false
    }
}, { tableName: 'subscription', timestamps: false });

Plan.hasMany(Subscription, { foreignKey: 'id_plan' });
Subscription.belongsTo(Plan, { foreignKey: 'id_plan' });

module.exports = Subscription;
