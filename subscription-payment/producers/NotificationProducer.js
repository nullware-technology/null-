require('dotenv').config();

const amqp = require("amqplib");
const NullPlusException = require('../domain/exception/NullPlusException');

class NotificationProducer {

    async publishWelcomeEmail(emailDTO) {
        try {
          const connection = await amqp.connect(process.env.RABBITMQ_ADDRESS);
          const channel = await connection.createChannel();
      
          await channel.assertQueue('send.email.queue', {
            durable: true,
            arguments: {
              'x-queue-type': 'quorum',
              'x-message-ttl': 600000,
            },
          });
      
          channel.sendToQueue('send.email.queue', Buffer.from(JSON.stringify(emailDTO)));
      
          await channel.close();
          await connection.close();
        } catch (error) {
          throw new NullPlusException('Erro ao publicar mensagem na fila.', 500);
        }
      }

}

module.exports = new NotificationProducer();