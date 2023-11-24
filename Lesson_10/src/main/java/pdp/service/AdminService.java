package pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pdp.domains.AuthUser;
import pdp.domains.City;
import pdp.domains.UserDetails;
import pdp.domains.Weather;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminService {

    private final JdbcTemplate jdbcTemplate;


    public List<AuthUser> getAllUsers() {
        var sql = "select * from authUser where role = 'USER' order by id";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AuthUser.class));
    }

    public UserDetails getUserDetails(Integer userId) {
        var sql = "select a.id,a.username,a.role,array_agg(c.name) as cities from authUser a full join subs s on a.id = s.userId" +
                " full join city c on c.id = s.cityId where a.id = ? and a.role = 'USER' group by a.id";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            List<String> cities = Arrays.asList((String[]) rs.getArray(4).getArray());
            return UserDetails.builder()
                    .userId(rs.getInt(1))
                    .username(rs.getString(2))
                    .userRole(rs.getString(3))
                    .userCities(cities)
                    .build();
        }, userId);
    }

    public void editUser(AuthUser authUser) {
        var sql = "update authUser set username = ? , role = ? where id = ?";
        jdbcTemplate.update(sql, authUser.getUsername(), authUser.getRole(), authUser.getId());
    }

    public AuthUser getUser(Integer id) {
        var sql = "select * from authUser where id = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(AuthUser.class), id);
    }

    public void deleteUser(Integer id) {
        var sql = "delete from authUser where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteCity(Integer id) {
        var sql = "delete from city where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public City getCity(Integer id) {
        var sql = "select * from city where id = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(City.class), id);
    }

    public void editCity(City city) {
        var sql = "update city set name = ? , country = ? , population = ? where id = ?";
        jdbcTemplate.update(sql, city.getName(), city.getCountry(), city.getPopulation(), city.getId());
    }

    public void addCity(City city) {
        var sql = "insert into city(name,country,population) values(?,?,?)";
        jdbcTemplate.update(sql, city.getName(), city.getCountry(), city.getPopulation());
    }

    public List<Weather> getWeatherData(Integer cityId) {
        var sql = "select * from weather where cityId = ? order by date desc";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Weather.class), cityId);
    }

    public Weather getWeatherDataById(Integer id) {
        var sql = "select * from weather where id = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Weather.class), id);
    }

    public void addWeatherData(Weather weather) {
        var sql = "insert into weather(cityId,cityName,date,temperature) values(?,?,?,?)";
        jdbcTemplate.update(sql, weather.getCityId(), weather.getCityName(), weather.getDate(), weather.getTemperature());
    }

    public void editWeatherData(Weather weather) {
        var sql = "update weather set date = ? , temperature = ? where id = ?";
        jdbcTemplate.update(sql, weather.getDate(), weather.getTemperature(), weather.getId());
    }

    public void deleteWeatherData(Integer id) {
        var sql = "delete from weather where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
