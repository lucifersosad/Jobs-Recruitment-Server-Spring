package spring.api.uteating.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import spring.api.uteating.entity.Admin;
import spring.api.uteating.entity.Employer;
import spring.api.uteating.entity.Role;
import spring.api.uteating.entity.User;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserService implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    private User user;

    private List<GrantedAuthority> authorities;

    public MyUserService(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user instanceof Employer) {
            authorities.add(new SimpleGrantedAuthority("EMPLOYER"));
        } else if (user instanceof Admin) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("CANDIDATE"));
        }
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getEmail() {
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
        return user.getStatus();
    }
}
