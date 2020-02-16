package com.sts.repository.search;

import com.sts.domain.ExtraParam;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ExtraParam} entity.
 */
public interface ExtraParamSearchRepository extends ElasticsearchRepository<ExtraParam, Long> {
}
