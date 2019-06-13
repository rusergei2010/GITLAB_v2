package com.data;

import java.util.Objects;


public class JobHistoryEntry {
    private final int duration;
    private final String position;
    private final String employer;

    public JobHistoryEntry(int duration, String position, String employer) {
        this.duration = duration;
        this.position = position;
        this.employer = employer;
    }

    public int getDuration() {
        return duration;
    }

    public String getPosition() {
        return position;
    }

    public String getEmployer() {
        return employer;
    }

    public JobHistoryEntry withDuration(int duration) {
        return new JobHistoryEntry(duration, position, employer);
    }

    public JobHistoryEntry withPosition(String position) {
        return new JobHistoryEntry(duration, position, employer);
    }

    public JobHistoryEntry withEmployer(String employer) {
        return new JobHistoryEntry(duration, position, employer);
    }

    @Override public String
    toString() {
        return "JobHistoryEntry{" +
                "duration=" + duration +
                ", position='" + position + '\'' +
                ", employer='" + employer + '\'' +
                '}';
    }

    @Override public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobHistoryEntry that = (JobHistoryEntry) o;
        return duration == that.duration &&
                Objects.equals(position, that.position) &&
                Objects.equals(employer, that.employer);
    }

    @Override public int hashCode() {
        return Objects.hash(duration, position, employer);
    }
}
