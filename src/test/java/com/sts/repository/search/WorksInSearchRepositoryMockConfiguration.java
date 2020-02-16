package com.sts.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link WorksInSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WorksInSearchRepositoryMockConfiguration {

    @MockBean
    private WorksInSearchRepository mockWorksInSearchRepository;

}
