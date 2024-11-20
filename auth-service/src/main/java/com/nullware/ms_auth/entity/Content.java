package com.nullware.ms_auth.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(name = "age_rating")
    private Integer ageRating;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> historyRecords;

    public Content(Long id, String title, String description, Integer ageRating, List<History> historyRecords) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ageRating = ageRating;
        this.historyRecords = historyRecords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Integer ageRating) {
        this.ageRating = ageRating;
    }

    public List<History> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(List<History> historyRecords) {
        this.historyRecords = historyRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
