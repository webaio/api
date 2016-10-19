package io.weba.api.domain.session;

import java.util.Objects;

public enum Interval {
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    QUARTER("quarter"),
    YEAR("year");

    private String interval;

    Interval(String interval) {
        this.interval = interval;
    }

    public static Interval from(String interval) {
        if(Objects.nonNull(interval)) {
            for(Interval intervalEnum: Interval.values()) {
                if(interval.equalsIgnoreCase(intervalEnum.toString())) {
                    return intervalEnum;
                }
            }
        }

        return DAY;
    }

    @Override
    public String toString() {
        return interval;
    }
}
