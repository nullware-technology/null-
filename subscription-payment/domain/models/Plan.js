const Sequelize = require("sequelize");
const connection = require("../../config/Database");

const Plan = connection.define('plan', {
    id: {
        type: Sequelize.INTEGER,
        autoIncrement: true,
        allowNull: false,
        primaryKey: true
    },
    name: {
        type: Sequelize.STRING,
        allowNull: false
    },
    description: {
        type: Sequelize.TEXT,
        allowNull: false
    },
    price: {
        type: Sequelize.FLOAT,
        allowNull: false
    },
    duration: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    idStripe: {
        type: Sequelize.STRING,
        field: 'id_stripe',
        allowNull: false
    }
}, {
    tableName: 'plan',
    timestamps: false
});

module.exports = Plan;
