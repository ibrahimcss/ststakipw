package com.sts.repository.search;

import com.sts.domain.OrderPaket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link OrderPaket} entity.
 */
public interface OrderPaketSearchRepository extends ElasticsearchRepository<OrderPaket, Long> {
}
