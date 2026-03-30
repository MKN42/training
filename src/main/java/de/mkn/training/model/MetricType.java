package de.mkn.training.model;

/**
 * The kind of measurable result captured after an exercise session.
 * New types can be added as further exercise domains are introduced.
 */
public enum MetricType {
    REPETITIONS,        // count of reps (physical)
    WEIGHT_KG,          // load used (physical)
    DURATION_SECONDS,   // total time held / performed
    SPEED_BPM,          // tempo reached (musical)
    ACCURACY_PERCENT,   // correctness score (cognitive / musical)
    CUSTOM              // free-form numeric value with a user-defined unit label
}
