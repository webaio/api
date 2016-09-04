package io.weba.api.ui.rest.security.oauth2.authorization;

import io.weba.api.domain.user.UserRepository;
import io.weba.api.ui.rest.security.user.UserDetailsServiceImpl;
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
}
