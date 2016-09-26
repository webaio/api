package io.weba.api.rest.security.user;

import io.weba.api.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsAdapter implements UserDetails {
    private final User domainUser;

    public UserDetailsAdapter(User domainUser) {
        this.domainUser = domainUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedCollection = new ArrayList<>();
        grantedCollection.add(new SimpleGrantedAuthority(this.domainUser.getRoles().getName()));

        return grantedCollection;
    }

    @Override
    public String getPassword() {
        return this.domainUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.domainUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
