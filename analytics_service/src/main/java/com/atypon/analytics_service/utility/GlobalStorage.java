package com.atypon.analytics_service.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalStorage {
    /*
     * The GlobalStorage is a configuration class which is used to retrieve values
     * that are shared between components, such as other service urls.
     * Values will be regarded as Beans, and whenever a value is needed it will be
     * accessed through the ApplicationContext provided by Spring.
     * */

    @Bean
    public String mongoApiUrl() {
        return "http://localhost:8002";
    }

    @Bean
    public String mysqlApiUrl() {
        return "http://localhost:8001";
    }
}
