package de.mkn.training.model;

/**
 * Which in-session support tool an exercise uses.
 * INTERVAL_TIMER — work/rest rounds (physical exercises)
 * METRONOME      — BPM-based tempo guide (musical exercises)
 * NONE           — no automated support needed
 */
public enum ExerciseSupportType {
    INTERVAL_TIMER,
    METRONOME,
    NONE
}
