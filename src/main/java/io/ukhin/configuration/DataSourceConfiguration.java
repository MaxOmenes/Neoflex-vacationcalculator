package io.ukhin.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Configuration
public class DataSourceConfiguration {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName("org.h2.Driver");
        driver.setUrl("jdbc:h2:mem:holiday");
        driver.setUsername("sa");
        driver.setPassword("sa");
        return driver;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("CREATE TABLE holidays (holiday_day INT, holiday_month INT)");

            jdbcTemplate.batchUpdate("INSERT INTO holidays (holiday_day, holiday_month) VALUES (?1, ?2)",
                    List.of(new Object[]{1, 1},
                            new Object[]{23, 2},
                            new Object[]{8, 3},
                            new Object[]{1, 5},
                            new Object[]{9, 5},
                            new Object[]{12, 6},
                            new Object[]{3, 10},
                            new Object[]{31, 12})
            );
        };
    }
}
