package io.weba.api.infrastructure.domain.session.elasticsearch;

import io.weba.api.domain.session.SessionCardinalityCriteria;
import io.weba.api.ui.rest.config.properties.ElasticsearchProperties;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class ResultFetcherImpl implements ResultFetcher {
    private Client client;
    private ElasticsearchProperties elasticsearchProperties;

    @Autowired
    public ResultFetcherImpl(Client client, ElasticsearchProperties elasticsearchProperties) {
        this.client = client;
        this.elasticsearchProperties = elasticsearchProperties;
    }

    @Override
    public SearchResponse fetchResponse(SessionCardinalityCriteria sessionCardinalityCriteria) {
        return client.prepareSearch(elasticsearchProperties.getEventsIndexName())
                .setTypes(elasticsearchProperties.getEventsIndexName())
                .addAggregation(new CardinalityAggregationFactory().create())
                .setQuery(new QueryRangeFactory().createRangeQuery(sessionCardinalityCriteria))
                .execute()
                .actionGet();
    }

    private class CardinalityAggregationFactory {
        DateHistogramBuilder create() {
            CardinalityBuilder cardinalityBuilder = AggregationBuilders
                    .cardinality(Configuration.aggregateSessionCardinalityName)
                    .field(Configuration.sessionFieldName);

            return AggregationBuilders
                    .dateHistogram(Configuration.aggregateDateHistogramName)
                    .interval(DateHistogramInterval.DAY)
                    .field(Configuration.dateFieldName)
                    .minDocCount(1)
                    .subAggregation(cardinalityBuilder);
        }
    }

    private class QueryRangeFactory {
        QueryBuilder createRangeQuery(SessionCardinalityCriteria sessionCardinalityCriteria) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                    .rangeQuery(Configuration.dateFieldName)
                    .timeZone(TimeZone.getTimeZone("UTC").getID())
                    .from(sessionCardinalityCriteria.from)
                    .to(sessionCardinalityCriteria.to);

            TermQueryBuilder termQueryBuilder = QueryBuilders
                    .termQuery(
                            Configuration.trackerIdFieldName,
                            sessionCardinalityCriteria.trackerIdentity.toString()
                    );

            return QueryBuilders.boolQuery()
                    .must(rangeQueryBuilder)
                    .must(termQueryBuilder);
        }
    }
}
