package io.weba.api.domain.site;

public class Tracker {
    private final String trackerContent;

    public Tracker(String trackerContent) {
        this.trackerContent = trackerContent;
    }

    public String getTrackerContent() {
        return this.trackerContent;
    }
}
