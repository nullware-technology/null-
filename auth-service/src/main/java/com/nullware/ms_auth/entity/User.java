package com.nullware.ms_auth.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles;

    // Construtor padrão
    public User() {
    }

    // Construtor completo
    public User(Long id, String name, String email, String password, LocalDate createdAt, Plan plan, List<Profile> profiles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.plan = plan;
        this.profiles = profiles;
    }

    // Método builder
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // Getters e Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    // Método adicional (como em Lombok)
    public String getSubscriptionPlanName() {
        return plan != null ? plan.getName() : "No active subscription";
    }

    // Equals e HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Classe Builder (em substituição ao @Builder)
    public static class UserBuilder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private LocalDate createdAt;
        private Plan plan;
        private List<Profile> profiles;

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder plan(Plan plan) {
            this.plan = plan;
            return this;
        }

        public UserBuilder profiles(List<Profile> profiles) {
            this.profiles = profiles;
            return this;
        }

        public User build() {
            return new User(id, name, email, password, createdAt, plan, profiles);
        }
    }
}
