package com.example.cursodsousa.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String userName;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource(){
        // Não é recomendado a utilização em produção, pois pode deixar a aplicação mais lenta
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(this.url);
        ds.setUsername(this.userName);
        ds.setPassword(this.password);
        ds.setDriverClassName(this.driver);

        return ds;
    }

    //@Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.url);
        config.setUsername(this.userName);
        config.setPassword(this.password);
        config.setDriverClassName(this.driver);

        config.setMaximumPoolSize(10); // Máximo de conexões liberadas
        config.setMinimumIdle(1); // Tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600 mil ms
        config.setConnectionTimeout(100000); // Timeout para conseguir uma conexão com o banco
        config.setConnectionTestQuery("Select 1"); // Query para teste de conexão com o banco


        return new HikariDataSource(config);
    }
}
