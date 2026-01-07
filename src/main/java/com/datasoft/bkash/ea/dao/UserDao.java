package com.datasoft.bkash.ea.dao;

import com.datasoft.bkash.ea.model.Users;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    List<Users> findAll();

    Optional<Users> findById(Integer id);

    int changeStatus(Integer id);

    List<Map<String, Object>> findAllByStatus(boolean status);

    Integer deleteById(Integer id);

    Integer save(Users user);

    Integer update(Users user);

    boolean hasPermission(Integer userId, String path);

    String getUsernameByLoginId(String name);
    Integer getUserIdByLoginId(String name);
    List<Map<String, Object>> getBeforeTask(Integer id);

    Integer imageDataUpdate(Integer imageId, Integer tempId);

    Object getUserTagTypeMaping(Integer id);
}
