const fs = require('fs').promises;
const path = require('path');

const EmailDTO = require('../domain/dto/EmailDTO');
const NotificationProducer = require('../producers/NotificationProducer');

class EmailService {

    async sendWelcomeEmail(email, customerName) {
        const emailTemplatePath = path.join(__dirname, '../templates', 'welcomeEmail.html');
        const data = await fs.readFile(emailTemplatePath, 'utf8');
        
        const emailContent = data.replace('{{name}}', customerName);

        const emailDTO = new EmailDTO(
            email, 
            "Bem vindo ao Null+", 
            emailContent
          );
          
          NotificationProducer.publishWelcomeEmail(emailDTO);
    }
    
}

module.exports = new EmailService();