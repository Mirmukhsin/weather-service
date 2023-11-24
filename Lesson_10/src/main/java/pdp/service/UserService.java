package pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pdp.config.security.SessionUser;
import pdp.domains.City;
import pdp.domains.Weather;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final SessionUser sessionUser;


    public List<City> getAllCities() {
        var sql = "select * from city order by id";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(City.class));
    }

    public void addUserSubsCities(Integer cityId) {
        var sql = "insert into subs (userId,cityId) values(?,?)";
        jdbcTemplate.update(sql, sessionUser.getUser().getId(), cityId);
    }

    public void deleteSubsCity(Integer cityId) {
        var sql = "delete from subs where userId = ? and cityId = ?";
        jdbcTemplate.update(sql, sessionUser.getUser().getId(), cityId);
    }

    public List<City> getUserSubsCities() {
        var sql = "select c.* from subs s inner join city c  on c.id = s.cityId where s.userId = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(City.class), sessionUser.getUser().getId());
    }

    public List<Weather> getWeatherData(Integer cityId, Integer kun) {
        var sql = "select * from weather where cityId = ? order by date desc limit  ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Weather.class), cityId, kun);
    }
}
