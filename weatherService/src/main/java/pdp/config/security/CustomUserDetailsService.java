package pdp.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pdp.domains.AuthUser;
import pdp.service.AuthUserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthUserService authUserService;

    public CustomUserDetailsService(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username"));
        return new CustomUserDetails(authUser);
    }
}
