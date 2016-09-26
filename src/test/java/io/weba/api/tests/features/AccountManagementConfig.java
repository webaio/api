package io.weba.api.tests.features;

import io.weba.api.domain.account.AccountRepository;
import io.weba.api.domain.role.RoleRepository;
import io.weba.api.domain.site.SiteRepository;
import io.weba.api.domain.user.UserRepository;
import io.weba.api.infrastructure.domain.account.inmemory.AccountRepositoryImpl;
import io.weba.api.infrastructure.domain.role.inmemory.RoleRepositoryImpl;
import io.weba.api.infrastructure.domain.site.inmemory.SiteRepositoryImpl;
import io.weba.api.infrastructure.domain.user.inmemory.UserRepositoryImpl;
import io.weba.api.tests.mock.AppEnvironmentImpl;
import io.weba.api.ui.rest.service.AppEnvironment;
import org.springframework.context.annotation.Bean;

public class AccountManagementConfig {
    @Bean(name = "accountRepository")
    public AccountRepository getAccountRepository() {
        return new AccountRepositoryImpl();
    }

    @Bean(name = "roleRepository")
    public RoleRepository getRoleRepository() {
        return new RoleRepositoryImpl();
    }

    @Bean(name = "userRepository")
    public UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }

    @Bean(name = "appEnvironment")
    public AppEnvironment getAppEnvironment() {
        return new AppEnvironmentImpl();
    }

    @Bean(name = "siteRepository")
    public SiteRepository getSiteRepository() {
        return new SiteRepositoryImpl();
    }
}
