package com.example.jdbcFilterQuery.Repository;


import com.example.jdbcFilterQuery.Models.UserSecurity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserSecurityRepository {
    private JdbcTemplate jdbcTemplate;

    public UserSecurityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserSecurity findByName(String name){
        String findByName = "SELECT * FROM USER_INFORMATION WHERE name = ?";
        RowMapper<UserSecurity> rowMapper = (rs, rowNum) -> new UserSecurity(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password")

        );
            return jdbcTemplate.query(findByName, rowMapper, name)
                    .stream()
                    .findFirst()
                    .orElse(null); // Optional kullanmadan null döner;

    }
    public String addUser(UserSecurity userSecurity){
        String addQuery= "INSERT INTO USER_INFORMATION (name, password) VALUES (?,?)";
        jdbcTemplate.update(addQuery, userSecurity.getName(), userSecurity.getPassword());
        return "Kullanici eklendi";
    }

}
