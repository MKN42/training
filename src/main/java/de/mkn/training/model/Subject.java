package de.mkn.training.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A training domain the user can practice.
 * Examples: "Strength Training", "Guitar", "Spanish Vocabulary"
 */
@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubjectCategory category;

    private String description;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    protected Subject() {}

    public Subject(String name, SubjectCategory category) {
        this.name = name;
        this.category = category;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public SubjectCategory getCategory() { return category; }
    public void setCategory(SubjectCategory category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Exercise> getExercises() { return exercises; }
}
