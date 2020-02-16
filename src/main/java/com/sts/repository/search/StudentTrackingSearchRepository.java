package com.sts.repository.search;

import com.sts.domain.StudentTracking;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link StudentTracking} entity.
 */
public interface StudentTrackingSearchRepository extends ElasticsearchRepository<StudentTracking, Long> {
}
