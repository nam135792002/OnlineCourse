package vn.edu.ut.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@NoArgsConstructor
@EnableJpaAuditing
public class DatasourceConfig {
    @Bean
    @ConditionalOnProperty(name = "spring.flyway.repair", havingValue = "true")
    public FlywayMigrationStrategy repairMigrateStrategy(){
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }
}
