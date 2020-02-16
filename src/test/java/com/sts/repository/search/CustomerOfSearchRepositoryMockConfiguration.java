package com.sts.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CustomerOfSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CustomerOfSearchRepositoryMockConfiguration {

    @MockBean
    private CustomerOfSearchRepository mockCustomerOfSearchRepository;

}
