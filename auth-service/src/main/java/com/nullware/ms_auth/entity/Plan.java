package com.nullware.ms_auth.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "id_stripe", nullable = false)
    private String idStripe;

    @Column(nullable = false)
    private Integer duration; // Duration of the plan in months

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions;

    public Plan(Long id, String name, String description, BigDecimal price, String idStripe, Integer duration, List<Subscription> subscriptions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.idStripe = idStripe;
        this.duration = duration;
        this.subscriptions = subscriptions;
    }

    public Plan() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIdStripe() {
        return idStripe;
    }

    public void setIdStripe(String idStripe) {
        this.idStripe = idStripe;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return Objects.equals(id, plan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
