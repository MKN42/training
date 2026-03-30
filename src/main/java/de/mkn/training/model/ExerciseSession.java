package de.mkn.training.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Records one completed (or in-progress) run of a single exercise.
 *
 * A session can be standalone (planItem = null) or part of a plan.
 * Metrics (reps, weight, BPM achieved, …) are stored as child SessionMetric rows
 * so new measurement types can be added without schema changes.
 */
@Entity
@Table(name = "exercise_sessions")
public class ExerciseSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    /** The plan item this session belongs to, if triggered via DICE. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_item_id")
    private ExercisePlanItem planItem;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    /** Null until the session is finished. */
    private LocalDateTime finishedAt;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionMetric> metrics = new ArrayList<>();

    protected ExerciseSession() {}

    public ExerciseSession(Exercise exercise, LocalDateTime startedAt) {
        this.exercise = exercise;
        this.startedAt = startedAt;
    }

    public void addMetric(SessionMetric metric) {
        metric.setSession(this);
        metrics.add(metric);
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public ExercisePlanItem getPlanItem() { return planItem; }
    public void setPlanItem(ExercisePlanItem planItem) { this.planItem = planItem; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getFinishedAt() { return finishedAt; }
    public void setFinishedAt(LocalDateTime finishedAt) { this.finishedAt = finishedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<SessionMetric> getMetrics() { return metrics; }
}
