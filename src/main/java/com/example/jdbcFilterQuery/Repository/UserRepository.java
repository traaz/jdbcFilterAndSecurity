package com.example.jdbcFilterQuery.Repository;
import com.example.jdbcFilterQuery.Models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public  List<User> getUserFromFilter(String filterName, String filterSurname, Integer filterAge){

        /*filterQuery += " AND name = ?"; şeklinde string birleştirme Java'da her seferinde yeni bir String nesnesi oluşturur. Bu, özellikle sorgular büyük ve karmaşık hale geldikçe performans sorunlarına yol açabilir.

StringBuilder kullanmak daha iyi çünkü string birleştirme işlemlerinde sürekli yeni nesneler oluşturulmaz. Bu daha verimli bir yaklaşımdır.*/


        StringBuilder filterQuery = new StringBuilder("SELECT * FROM USERS WHERE 1=1");
        List<Object> parametreList = new ArrayList<>();

        if(filterName != null && !filterName.isBlank()){
            filterQuery.append(" AND name = ?");
            parametreList.add(filterName);
        }
        if(filterSurname != null && !filterSurname.isBlank()){
            filterQuery.append(" AND surname = ?");
            parametreList.add(filterSurname);

        }
        if(filterAge != null){
            filterQuery.append(" AND age = ?");
            parametreList.add(filterAge);

        }

        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("age")
        );


        return jdbcTemplate.query(filterQuery.toString(), parametreList.toArray(), rowMapper);
    }

    public List<User> getAllUser(){
        String getAllListQuery= "SELECT * FROM USERS";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("age")
        );
      return  jdbcTemplate.query(getAllListQuery, rowMapper);
    }
    public User getUserById(int id){
        String getUserByIdQuery = "SELECT * FROM USERS WHERE id = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("age")
        );
        //query birden fala sonuc icin uygundur. o yuzden burda kullanmadik
        return jdbcTemplate.queryForObject(getUserByIdQuery, rowMapper, id);
    }
    public String updateUser(int id,User user){
        String updateUserQuery = "UPDATE USERS SET name = ?, surname = ? , age = ? WHERE id = ?";
         jdbcTemplate.update(updateUserQuery,user.getName(), user.getSurname(), user.getAge(), id);
         return "Kullanici guncellendi";
    }
   /* public User findByName(String name){
        String findByName = "SELECT * FROM USERS WHERE name = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getInt("age")
        );
        return jdbcTemplate.queryForObject(findByName, rowMapper, name);
    }*/
}
