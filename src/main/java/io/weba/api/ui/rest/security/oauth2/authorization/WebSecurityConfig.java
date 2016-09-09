package io.weba.api.ui.rest.security.oauth2.authorization;

import io.weba.api.domain.user.UserRepository;
import io.weba.api.ui.rest.security.user.UserDetailsServiceImpl;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.userAuthenticationProvider())
                .jdbcAuthentication()
                .dataSource(this.dataSource);
    }

    @Override
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManager() throws Exception {
        List<AuthenticationProvider> list = new ArrayList<>();
        list.add(this.userAuthenticationProvider());

        return new ProviderManager(list);
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(new UserDetailsServiceImpl(this.userRepository));
        return provider;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("mytest.jks"), "mypass".toCharArray()
        );
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));

        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsManager());
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
        manager.setDataSource(dataSource);
        manager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
        manager.setAuthoritiesByUsernameQuery("SELECT u.username, r.name FROM users u " +
                "LEFT JOIN roles r ON u.role_id = r.id WHERE u.username = ?");

        return manager;
    }
}
