class EmailDTO {

    constructor(emailTo, subject, text) {
        this.emailTo = emailTo;
        this.subject = subject;
        this.text = text;
    }

}

module.exports = EmailDTO;