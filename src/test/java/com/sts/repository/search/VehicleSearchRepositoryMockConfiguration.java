package com.sts.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link VehicleSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class VehicleSearchRepositoryMockConfiguration {

    @MockBean
    private VehicleSearchRepository mockVehicleSearchRepository;

}
