package de.mkn.training.model;

public enum PlanStatus {
    PENDING,      // generated, not yet started
    IN_PROGRESS,  // user is currently working through it
    COMPLETED,    // all items finished
    CANCELLED
}
