package pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pdp.domains.AuthUser;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUserService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;


    public void saveUser(AuthUser authUser) {
        var sql = "insert into authUser(username,password,role) values(?,?,?)";
        jdbcTemplate.update(sql, authUser.getUsername(), passwordEncoder.encode(authUser.getPassword()), "USER");
    }

    public Optional<AuthUser> findByUsername(String username) {
        var sql = "select * from authUser where username = ?";
        try {
            AuthUser authUser = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(AuthUser.class), username);
            return Optional.of(authUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
