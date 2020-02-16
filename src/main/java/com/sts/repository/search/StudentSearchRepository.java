package com.sts.repository.search;

import com.sts.domain.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Student} entity.
 */
public interface StudentSearchRepository extends ElasticsearchRepository<Student, Long> {
}
