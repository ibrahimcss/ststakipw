package com.sts.repository.search;

import com.sts.domain.FamilyMember;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FamilyMember} entity.
 */
public interface FamilyMemberSearchRepository extends ElasticsearchRepository<FamilyMember, Long> {
}
