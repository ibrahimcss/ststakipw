package com.sts.repository.search;

import com.sts.domain.CustomerOf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CustomerOf} entity.
 */
public interface CustomerOfSearchRepository extends ElasticsearchRepository<CustomerOf, Long> {
}
