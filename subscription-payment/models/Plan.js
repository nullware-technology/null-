const Sequelize = require("sequelize");
const connection = require("../config/Database");

const Plan = connection.define('plan', {
    id_plan: {
        type: Sequelize.UUID,
        allowNull: false,
        defaultValue: Sequelize.UUIDV4,
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
    length: {
        type: Sequelize.INTEGER,
        allowNull: false
    },
    id_stripe: {
        type: Sequelize.STRING,
        allowNull: false
    }
}, {
    tableName: 'plan',
    timestamps: false
});

module.exports = Plan;
