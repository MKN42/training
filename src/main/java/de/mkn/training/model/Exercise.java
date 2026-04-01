package de.mkn.training.model;

import jakarta.persistence.*;

/**
 * A single reusable exercise definition.
 *
 * The {@code subtype} field is interpreted relative to the parent Subject's category:
 *   PHYSICAL  → body part (e.g. "CHEST", "LEGS", "CORE", "FULL_BODY")
 *   MUSICAL   → exercise kind (e.g. "SONG", "LICK", "TECHNICAL")
 *   COGNITIVE → drill type (e.g. "FLASHCARD", "TRANSLATION", "LISTENING")
 *   OTHER     → free text
 *
 * Support fields are only relevant for the matching ExerciseSupportType:
 *   INTERVAL_TIMER → intervalWorkSeconds, intervalRestSeconds, intervalSets
 *   METRONOME      → metronomeBpm
 */
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    /** Domain-specific classification within the Subject's category. */
    private String subtype;

    /** Relative path or URL to an image illustrating the exercise. */
    private String imagePath;

    /** Link to an instructional video. */
    private String videoUrl;

    /** Deep-link or URL to a companion app (e.g. a specific tab/position app). */
    private String appUrl;

    /** Suggested default duration in minutes, used by the DICE feature. */
    private Integer defaultDurationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseSupportType supportType = ExerciseSupportType.NONE;

    // --- Interval Timer fields (PHYSICAL) ---
    private Integer intervalWorkSeconds;
    private Integer intervalRestSeconds;
    private Integer intervalSets;

    // --- Metronome field (MUSICAL) ---
    private Integer metronomeBpm;

    protected Exercise() {}

    public Exercise(Subject subject, String name) {
        this.subject = subject;
        this.name = name;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubtype() { return subtype; }
    public void setSubtype(String subtype) { this.subtype = subtype; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getAppUrl() { return appUrl; }
    public void setAppUrl(String appUrl) { this.appUrl = appUrl; }

    public Integer getDefaultDurationMinutes() { return defaultDurationMinutes; }
    public void setDefaultDurationMinutes(Integer defaultDurationMinutes) {
        this.defaultDurationMinutes = defaultDurationMinutes;
    }

    public ExerciseSupportType getSupportType() { return supportType; }
    public void setSupportType(ExerciseSupportType supportType) { this.supportType = supportType; }

    public Integer getIntervalWorkSeconds() { return intervalWorkSeconds; }
    public void setIntervalWorkSeconds(Integer intervalWorkSeconds) {
        this.intervalWorkSeconds = intervalWorkSeconds;
    }

    public Integer getIntervalRestSeconds() { return intervalRestSeconds; }
    public void setIntervalRestSeconds(Integer intervalRestSeconds) {
        this.intervalRestSeconds = intervalRestSeconds;
    }

    public Integer getIntervalSets() { return intervalSets; }
    public void setIntervalSets(Integer intervalSets) { this.intervalSets = intervalSets; }

    public Integer getMetronomeBpm() { return metronomeBpm; }
    public void setMetronomeBpm(Integer metronomeBpm) { this.metronomeBpm = metronomeBpm; }
}
