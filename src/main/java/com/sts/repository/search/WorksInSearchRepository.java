package com.sts.repository.search;

import com.sts.domain.WorksIn;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link WorksIn} entity.
 */
public interface WorksInSearchRepository extends ElasticsearchRepository<WorksIn, Long> {
}
