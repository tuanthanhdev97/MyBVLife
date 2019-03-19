package app.com.baoviet.interfaceclass;

import java.io.Serializable;
import java.util.List;

public interface UserDetails extends Serializable {
    List<GrantedAuthority> getAuthorities();

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();
}
