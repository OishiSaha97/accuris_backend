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

    @Override
    public int changeStatus(Integer id) {
        final String sql = "UPDATE `user` SET `status` = IF(`status` = b'1', b'0', b'1') WHERE `id`=?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Map<String, Object>> findAllByStatus(boolean status) {
        return null;
    }

//    @Override
//    public List<Map<String, Object>> findAllByStatus(boolean status) {
//        final String query = "SELECT * FROM `user` u WHERE u.`status`=?";
//        return jdbcTemplate.queryForList(query, new Object[]{status}, new BeanPropertyRowMapper<>(User.class));
//    }

    @Override
    public Integer deleteById(Integer id) {
        final String DELETE_USER = "DELETE FROM `user` u WHERE u.id=?";
        return jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public Integer save(Users user) {
        final String INSERT_USER = "INSERT INTO `user`(`updated_at`,`active`,`designation`,`email`,`emp_id`,`image_url`,`ip_number`,`iscao`,`login_id`,`menu_item`,`password`,`phone_number`,`role_param`,`status`,`user_name`,`user_type`,`create_by_id`,`image_id`,`update_by_id`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(INSERT_USER, user.getUpdatedAt(),user.getActive(),user.getDesignation(),user.getEmail(),user.getEmpId(),user.getImageUrl(),user.getIpNumber(),user.getIscao(),user.getLoginId(),user.getMenuItem(),user.getPassword(),user.getPhoneNumber(),user.getRoleParam(),user.getStatus(),user.getUserName(),user.getUserType(),user.getCreatedByUserId(),user.getImageId(),user.getUpdatedByUserId());
    }

    @Override
    public Integer update(Users user) {
        // update: active, designation, iscao, menuItem, roleParam, status, userType
        final  String UPDATE_USER = "UPDATE `user` u SET u.`active`=?, u.`designation`=?, u.`iscao`= ?,u.`menu_item`=?,u.`role_param`=?,u.`status`=?,u.`user_type`=? WHERE u.`id`=?";
        return jdbcTemplate.update(UPDATE_USER, user.getActive(),user.getDesignation(),user.getIscao(),user.getMenuItem(),user.getRoleParam(),user.getStatus(),user.getUserType());
    }

    @Override
    public boolean hasPermission(Integer userId, String path) {
        // update: active, designation, iscao, menuItem, roleParam, status, userType
        try{
            final  String QUERY = "SELECT COUNT(DISTINCT t.url) as result from `user_roles` ur LEFT JOIN `role_tasks` r on (ur.roles_id = r.role_id) LEFT JOIN `task` t on (t.id = r.tasks_id) where ur.user_id = ? and t.url =?";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(QUERY, userId, path);
            if(Objects.nonNull(list) && !list.isEmpty() && Objects.nonNull(list.get(0).get("result"))){
                return 0 < Integer.parseInt(list.get(0).get("result").toString());
            }
        } catch (Exception ignored){}
        return false;
    }

    @Override
    public String getUsernameByLoginId(String loginId) {
        final String query = "SELECT user_name FROM `user` WHERE login_id=?";
        List<Map<String, Object>> user = jdbcTemplate.queryForList(query, loginId);
        return user.isEmpty() || Objects.isNull(user.get(0).get("user_name"))? "anonymousUser" :  user.get(0).get("user_name").toString() ;
    }
    @Override
    public Integer getUserIdByLoginId(String loginId) {
        final String query = "SELECT id FROM `user` WHERE login_id=?";
        List<Map<String, Object>> user = jdbcTemplate.queryForList(query, loginId);
        return user.isEmpty() || Objects.isNull(user.get(0).get("id"))? null :  (Integer)user.get(0).get("id") ;
    }


    @Override
    public List<Map<String, Object>> getBeforeTask(Integer id) {
        String sql = "SELECT ta.id AS task_id, ta.task_name, st.id AS super_task_id, st.task_name AS super_task " +
                "FROM role_tasks rt " +
                "LEFT JOIN task ta ON rt.tasks_id = ta.id " +
                "LEFT JOIN task st ON ta.super_task_id = st.id " +
                "WHERE role_id = ? ";

        // RowMapper to map each row of the ResultSet to a Map<String, Object>
        RowMapper<Map<String, Object>> rowMapper = (rs, rowNum) -> {
            Map<String, Object> rowMap = new java.util.HashMap<>();
            rowMap.put("task_id", rs.getInt("task_id"));
            rowMap.put("task_name", rs.getString("task_name"));
            rowMap.put("super_task_id", rs.getInt("super_task_id"));
            rowMap.put("super_task", rs.getString("super_task"));
            return rowMap;
        };

        // Execute the query and return the list of maps
        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
    }

    @Override
    public Integer imageDataUpdate(Integer imageId, Integer tempId) {
        String sql = "UPDATE `modified_info_history` SET `image_id` = ? where id = ?";
        return jdbcTemplate.update(sql, imageId,tempId);

    }

    @Override
    public Object getUserTagTypeMaping(Integer userId) {
        final String query = "SELECT user_tag_id, user_tag_type_id FROM user_tag_tag_type_mapping WHERE user_id = ?";

        return jdbcTemplate.query(query, new Object[]{userId}, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userTagId", rs.getInt("user_tag_id"));
            map.put("userTagTypeId", rs.getInt("user_tag_type_id"));
            return map;
        });
    }


}
