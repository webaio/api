package io.weba.api.infrastructure.domain.session.elasticsearch;

public class Configuration {
    public static final String sessionFieldName = "session.id";
    public static final String dateFieldName = "dates.client";
    public static final String trackerIdFieldName = "payload.tracker_id";
    public static final String aggregateDateHistogramName = "dates_histogram";
    public static final String aggregateSessionCardinalityName = "sessions_cardinality";
}
