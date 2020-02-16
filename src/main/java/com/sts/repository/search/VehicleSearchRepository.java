package com.sts.repository.search;

import com.sts.domain.Vehicle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Vehicle} entity.
 */
public interface VehicleSearchRepository extends ElasticsearchRepository<Vehicle, Long> {
}
