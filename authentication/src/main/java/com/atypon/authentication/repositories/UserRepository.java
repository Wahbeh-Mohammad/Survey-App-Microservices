package com.atypon.authentication.repositories;

import com.atypon.authentication.models.User;
import com.atypon.authentication.repositories.model_mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Profile("database")
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User fetchUser(User requestUser) {
        String username = requestUser.getUsername(), password = requestUser.getPassword();
        if(username == null || password == null) return null;

        String sqlStatement = "SELECT * FROM users WHERE username = ? AND password = ?;";
        List<User> users = jdbcTemplate.query(sqlStatement, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, username);
                ps.setString(2, password);
            }
        }, new UserMapper());

        if(users.isEmpty()) return null;
        return users.get(0);
    }

    public User createUser(User requestUser) {
        String username = requestUser.getUsername(), password = requestUser.getPassword();
        if(username == null || password == null) return null;

        String sqlStatement = "INSERT INTO users(username, password) VALUES (?, ?);";
        jdbcTemplate.update(sqlStatement, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, username);
                ps.setString(2, password);
            }
        });
        // Check if the new User was created.
        return fetchUser(requestUser); // heads up, this might return null.
    }
}
