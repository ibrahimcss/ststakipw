package com.sts.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PaketDetaySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PaketDetaySearchRepositoryMockConfiguration {

    @MockBean
    private PaketDetaySearchRepository mockPaketDetaySearchRepository;

}
