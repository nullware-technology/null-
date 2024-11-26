const PlansEnum = Object.freeze({
    BASICO_MENSAL: { priceId: 'price_1QHqk5P7uKm5lqwYzrDIWavu', tier: 1 },
    PREMIUM_MENSAL: { priceId: 'price_1QHqkiP7uKm5lqwYgEqGGDsK', tier: 2 },
    BASICO_ANUAL: { priceId: 'price_1QHqk5P7uKm5lqwYmnlYuuBK', tier: 1 },
    PREMIUM_ANUAL: { priceId: 'price_1QHqkiP7uKm5lqwY7cI2m4gH', tier: 2 },

    getPlanById: function(id) {
        return Object.values(this).find(plan => plan.priceId === id);
    }
});

module.exports = PlansEnum;