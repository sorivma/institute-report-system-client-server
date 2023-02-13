package com.institute.instituteserver.security;


import com.institute.instituteserver.entity.Privilege;
import com.institute.instituteserver.entity.Role;
import com.institute.instituteserver.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@AllArgsConstructor
public record SecurityUser(User user) implements UserDetails {

    @Override
    public User user() {
        return user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> roles = user.getRoles();
        Collection<Privilege> privileges = new ArrayList<>();
        for (Role role : roles) {
            privileges.addAll(role.getPrivileges());
        }
        return privileges;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
