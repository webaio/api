package io.weba.api.ui;

import io.weba.api.application.base.DomainEventPublisher;
import io.weba.api.domain.account.Account;
import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.timezone.TimezoneRepository;
import io.weba.api.infrastructure.application.DomainEventPublisherImpl;
import io.weba.api.infrastructure.domain.account.postgres.AccountRepositoryImpl;
import io.weba.api.infrastructure.domain.timezone.TimezoneRepositoryImpl;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "io.weba")
public class ApplicationContextConfig {

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/weba");
        dataSource.setUsername("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("io.weba.api.domain");

        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
