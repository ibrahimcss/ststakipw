package com.sts.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link OrderPaketSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OrderPaketSearchRepositoryMockConfiguration {

    @MockBean
    private OrderPaketSearchRepository mockOrderPaketSearchRepository;

}
