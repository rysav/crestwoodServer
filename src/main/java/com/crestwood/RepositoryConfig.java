package com.crestwood;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by ryan on 10/9/17.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.crestwood"})
@EnableJpaRepositories(basePackages = {"com.crestwood"})
@EnableTransactionManagement
public class RepositoryConfig {
}
