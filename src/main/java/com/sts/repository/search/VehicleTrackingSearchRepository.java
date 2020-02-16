package com.sts.repository.search;

import com.sts.domain.VehicleTracking;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link VehicleTracking} entity.
 */
public interface VehicleTrackingSearchRepository extends ElasticsearchRepository<VehicleTracking, Long> {
}
