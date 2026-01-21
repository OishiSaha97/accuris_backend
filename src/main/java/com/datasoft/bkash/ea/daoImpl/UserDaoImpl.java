package com.datasoft.bkash.ea.daoImpl;

import com.datasoft.bkash.ea.dao.UserDao;
import com.datasoft.bkash.ea.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
//    private final JdbcTemplate jdbcTemplate;
//
//    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

//    @Override
//    public List<Users> findAll() {
//       final String FIND_ALL_USERS = "SELECT * FROM `user`";
//        return jdbcTemplate.query(FIND_ALL_USERS,'');
//    }

    @Override
    public List<Users> findAll() {
        return null;
    }

    @Override
    public Optional<Users> findById(Integer id) {
        final String FIND_USER_BY_ID = "SELECT * FROM `user` u WHERE u.`id`=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID
                ,new Object[]{id},new BeanPropertyRowMapper<>(Users.class)));
    }
}
