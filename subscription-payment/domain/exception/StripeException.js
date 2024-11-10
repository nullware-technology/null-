class StripeException extends Error {
    constructor(error) {
        const parsedData = StripeException.parseErrorMessage(error);
        super(parsedData.message);
        this.name = "StripeException";
        this.status = parsedData.status;
    }

    static parseErrorMessage(error) {
        switch (error.code) {
            case 'resource_missing':
                return { message: 'Usuário não encontrado no Stripe.', status: 404 };
            default:
                return { message: 'Usuário não encontrado no Stripe.', status: 500 };
        }
    }

}

module.exports = StripeException;