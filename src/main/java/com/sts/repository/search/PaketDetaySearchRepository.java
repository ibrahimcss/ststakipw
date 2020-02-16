package com.sts.repository.search;

import com.sts.domain.PaketDetay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PaketDetay} entity.
 */
public interface PaketDetaySearchRepository extends ElasticsearchRepository<PaketDetay, Long> {
}
