package io.weba.api.infrastructure.domain.session.elasticsearch;

import io.weba.api.domain.session.SessionCardinality;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperImpl implements Mapper {
    @Override
    public List<SessionCardinality> mapResult(SearchResponse response) {
        List<SessionCardinality> list = new ArrayList<>();

        Histogram histogram = response.getAggregations().get(Configuration.aggregateDateHistogramName);
        List<? extends Histogram.Bucket> buckets = histogram.getBuckets();
        for(Histogram.Bucket bucket: buckets) {
            for(Aggregation cardinality: bucket.getAggregations().asList()){
                Long value = ((Cardinality) cardinality).getValue();
                DateTime date = (DateTime)bucket.getKey();
                list.add(new SessionCardinality(
                        date.toDate(),
                        value
                ));

            }
        }

        return list;
    }
}
