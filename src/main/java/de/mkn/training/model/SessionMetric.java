package de.mkn.training.model;

import jakarta.persistence.*;

/**
 * A single measurable result recorded during or after an ExerciseSession.
 *
 * Examples:
 *   REPETITIONS / 12           → 12 reps
 *   WEIGHT_KG   / 80           → 80 kg load
 *   SPEED_BPM   / 120          → reached 120 BPM on the metronome
 *   CUSTOM      / 95 / "words" → 95 words memorised (custom unit label)
 */
@Entity
@Table(name = "session_metrics")
public class SessionMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private ExerciseSession session;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetricType metricType;

    @Column(nullable = false)
    private Double value;

    /**
     * Human-readable unit label. Pre-filled for known MetricTypes,
     * user-defined for CUSTOM (e.g. "words", "pages", "rounds").
     */
    private String unit;

    protected SessionMetric() {}

    public SessionMetric(MetricType metricType, Double value) {
        this.metricType = metricType;
        this.value = value;
        this.unit = defaultUnit(metricType);
    }

    public SessionMetric(MetricType metricType, Double value, String unit) {
        this.metricType = metricType;
        this.value = value;
        this.unit = unit;
    }

    private static String defaultUnit(MetricType type) {
        return switch (type) {
            case REPETITIONS -> "reps";
            case WEIGHT_KG -> "kg";
            case DURATION_SECONDS -> "s";
            case SPEED_BPM -> "BPM";
            case ACCURACY_PERCENT -> "%";
            case CUSTOM -> "";
        };
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public ExerciseSession getSession() { return session; }
    public void setSession(ExerciseSession session) { this.session = session; }

    public MetricType getMetricType() { return metricType; }
    public void setMetricType(MetricType metricType) { this.metricType = metricType; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
