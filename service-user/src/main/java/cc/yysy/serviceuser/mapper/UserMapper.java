package cc.yysy.serviceuser.mapper;


import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.entity.UserAreaSubscription;
import cc.yysy.utilscommon.entity.UserObstacleResponsibility;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


public interface UserMapper {

//    private Integer id;
//    private String username;
//    private String userPhone;
//    private String password;
//    private String username;
//    private String real_name;

    @Insert("INSERT INTO `user`(`username`,`phone_number`,`password`,`real_name`) VALUES (" +
            "#{record.username},#{record.phoneNumber},#{record.password},#{record.realName}" +
            ");")
    int insertUser(@Param("record") User record);

    @Select("select password from user where phone_number = #{record}")
    String selectPassword(@Param("record")String userPhone);

    @Select("select phone_number from user where phone_number = #{record} or username = #{record} or real_name = #{record}")
    String selectPhoneNumber(@Param("record") String loginName);

    @Select("select * from user where phone_number = #{record}")
    User selectByPhoneNumber(@Param("record") String userPhone);


//
//    @Update("update user set " +
//            "username = #{record.phoneNumber}," +
//            "real_name = #{record.realName}," +
//            "password = #{record.password}," +
//            "user_type = #{record.userType} " +
//            "where #{record.phoneNumber} = phone_number")

//    @Update({"<script> UPDATE task " +
//            "<set>" +
//            "        <if test='record.alarm != null and record.alarm != \"\" '> alarm = '${record.alarm}', </if>" +
//            "        <if test='record.alarm == \"\" '> alarm = null, </if>" +
//            "        <if test='record.content != null'> content = '${record.content}', </if>" +
//            "        <if test='record.date != null'> date = '${record.date}',</if>" +
//            "        <if test='record.isFinish != null'> is_finish = ${record.isFinish},</if>" +
//            "        <if test='record.isPeriod != null'> is_period = ${record.isPeriod},</if>" +
//            "        <if test='record.period != null'> period = '${record.period}',</if>" +
//            "        <if test='record.time != null'> time = '${record.time}',</if>" +
//            "        <if test='record.type != null'> type = '${record.type}',</if>" +
//            "        <if test='record.subtasks != null'> subtasks = '${record.subtasks}',</if>" +
//            "</set>" +
//            "WHERE user_phone = '${record.userPhone}' and task_id = ${record.taskId}; " +
//            "</script>"})
@Update("<script> update user " +
        "<set>" +
            "<if test='record.username != null'> username = #{record.username}, </if>" +
            "<if test='record.password != null'> password = #{record.password}, </if>" +
            "<if test='record.realName != null'> real_name = #{record.realName}, </if>" +
            "<if test='record.userType != null'> user_type = #{record.userType}, </if>" +
        "</set>" +
        "where phone_number = #{record.phoneNumber}; " +
        "</script>")
    int updateByPhoneNumber(@Param("record") User user);

    @Select("select id,phone_number,username,real_name,user_type from user")
    List<User> selectList();

    @Select("select id,phone_number as phoneNumber," +
            "username,real_name as realName," +
            "user_type as userType from user where id = #{listQuery.search} or " +
            "phone_number like concat('%',#{listQuery.search,jdbcType=VARCHAR},'%') or " +
            "username like concat('%',#{listQuery.search,jdbcType=VARCHAR},'%') or " +
            "real_name like concat('%',#{listQuery.search,jdbcType=VARCHAR},'%') " +
            "limit ${(listQuery.page-1)*listQuery.limit},#{listQuery.limit}")
    List<User>getUserListByQuery(@Param("listQuery") Map<String,Object> query);


    @Select("select count(*) from user where id = #{listQuery.search} or " +
            "phone_number = #{listQuery.search} or " +
            "username like concat('%',#{listQuery.search,jdbcType=VARCHAR},'%') or " +
            "real_name like concat('%',#{listQuery.search,jdbcType=VARCHAR},'%')")
    int countUserListByQuery(@Param("listQuery") Map<String,Object> query);


    @Update("update user set user_type = #{userType} where id = #{userId} ")
    int updateUserType(@Param("userId")Integer userId, @Param("userType")String userType);

    @Delete("delete from user where id = #{userId} ")
    int deleteUser(@Param("userId") Integer userId);
}