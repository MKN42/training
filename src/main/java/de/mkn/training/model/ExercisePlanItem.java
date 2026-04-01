package de.mkn.training.model;

import jakarta.persistence.*;

/**
 * One exercise slot within an ExercisePlan.
 * Stores the position in the plan and how much time is allocated to it.
 */
@Entity
@Table(name = "exercise_plan_items")
public class ExercisePlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private ExercisePlan plan;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    /** 0-based position within the plan. */
    @Column(nullable = false)
    private int orderIndex;

    /** Minutes allocated to this exercise in the generated plan. */
    private Integer allocatedDurationMinutes;

    protected ExercisePlanItem() {}

    public ExercisePlanItem(Exercise exercise, int orderIndex, Integer allocatedDurationMinutes) {
        this.exercise = exercise;
        this.orderIndex = orderIndex;
        this.allocatedDurationMinutes = allocatedDurationMinutes;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public ExercisePlan getPlan() { return plan; }
    public void setPlan(ExercisePlan plan) { this.plan = plan; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }

    public Integer getAllocatedDurationMinutes() { return allocatedDurationMinutes; }
    public void setAllocatedDurationMinutes(Integer allocatedDurationMinutes) {
        this.allocatedDurationMinutes = allocatedDurationMinutes;
    }
}
