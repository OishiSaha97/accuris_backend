package com.datasoft.bkash.ea.dao;

import com.datasoft.bkash.ea.model.Users;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    List<Users> findAll();
    Optional<Users> findById(Integer id);


}
