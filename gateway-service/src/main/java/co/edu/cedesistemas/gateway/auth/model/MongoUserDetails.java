package co.edu.cedesistemas.gateway.auth.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@JsonDeserialize(as = MongoUserDetails.class)
public class MongoUserDetails implements UserDetails {
    private String username;
    private String password;
    private Integer active;
    private boolean locked;
    private boolean expired;
    private boolean enabled;
    private List<GrantedAuthority> grantedAuthorities;

    public MongoUserDetails(String username, String password, Integer active, boolean locked, boolean expired,
                            boolean enabled, String [] authorities) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.locked = locked;
        this.expired = expired;
        this.enabled = enabled;
        this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
    }

    public MongoUserDetails(String username, String[] authorities) {
        this.username = username;
        this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
