package com.sts.repository.search;

import com.sts.domain.TravelPath;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TravelPath} entity.
 */
public interface TravelPathSearchRepository extends ElasticsearchRepository<TravelPath, Long> {
}
