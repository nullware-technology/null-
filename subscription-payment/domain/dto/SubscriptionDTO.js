class SubscriptionDTO {

    constructor(name, card, created, nextPayment, amount, duration) {
        this.name = name;
        this.card = {
            brand: card.brand.toUpperCase(),
            number: "**** " + card.last4,
            expDate: card.exp_month + '/' + card.exp_year
        }
        this.clientSice = new Date(created * 1000).toISOString();
        this.nextPayment = new Date(nextPayment * 1000).toISOString();
        this.amount = amount / 100;
        this.duration = duration;
    }

}

module.exports = SubscriptionDTO;