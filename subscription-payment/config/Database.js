require('dotenv').config();

const Sequelize = require ("sequelize");

const connection = new Sequelize({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    username: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    dialect: "postgres",        
    timezone: '-03:00'
  });
  
connection.authenticate().then(() => {
    console.log('Banco de dados conectado.');
}).catch((error) => {
    console.log(error);
});

module.exports = connection;
