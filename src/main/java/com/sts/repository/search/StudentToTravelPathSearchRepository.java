package com.sts.repository.search;

import com.sts.domain.StudentToTravelPath;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StudentToTravelPath} entity.
 */
public interface StudentToTravelPathSearchRepository extends ElasticsearchRepository<StudentToTravelPath, Long> {
}
