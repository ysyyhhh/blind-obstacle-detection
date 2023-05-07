package cc.yysy.serviceobstacle.mapper;

import cc.yysy.serviceobstacle.Bean.ListQuery;
import cc.yysy.serviceobstacle.Bean.ObstacleDetail;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.entity.ObstacleList;
import cc.yysy.utilscommon.entity.User;
import cc.yysy.utilscommon.entity.UserObstacleResponsibility;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;
import java.util.Map;

public interface ObstacleMapper {
    @Update("update obstacle set " +
            "processing_time = #{record.processingTime}," +
            "processing_status = #{record.processingTime}," +
            "processor_id = #{record.processorId} " +
            "where id = #{record.id}")
    int dealObstacle(@Param("record")Obstacle obstacle);

    @Select("select * from obstacle where location like %#{fullName}%")
    List<Obstacle> getObstaclesByFullName(@Param("fullName")String fullName);

//     /**
//     * listQuery: {
//     *         page: 1,
//     *         limit: 20,
//     *         area: undefined,
//     *         type: undefined,
//     *         processingStatus: undefined,
//     *         processingStartTime: undefined,
//     *         processingEndTime: undefined,
//     *         discoveryStartTime: undefined,
//     *         discoveryEndTime: undefined,
//     *         processorId: undefined
//     */
    @Select("<script> select obstacle.id,obstacle.type,obstacle.location," +
            "obstacle.discovery_time as discoveryTime,obstacle.processing_time as processingTime,obstacle.processor_id as processorId," +
            "obstacle.processing_status as processingStatus,obstacle.image_path as imagePath, " +
            "user.real_name as realName "+
            "from obstacle left join user on obstacle.processor_id = user.id " +
            "<where>" +
            "<if test='listQuery.area != null'> and location like concat('%',#{listQuery.area,jdbcType=VARCHAR},'%') </if>" +
            "<if test='listQuery.type != null'> and type = #{listQuery.type} </if>" +
            "<if test='listQuery.processingStatus != null'> and processing_status = #{listQuery.processingStatus} </if>" +
            "<if test='listQuery.processingStartTime != null and  listQuery.processingEndTime != null' > and processing_time between #{listQuery.processingStartTime} and #{listQuery.processingEndTime} </if>" +
            "<if test='listQuery.discoveryStartTime != null and listQuery.discoveryEndTime != null'> and discovery_time between #{listQuery.discoveryStartTime} and #{listQuery.discoveryEndTime} </if>" +
            "<if test='listQuery.processorId != null'> and processor_id = #{listQuery.processorId}</if>" +
            "</where>" +
            "limit ${(listQuery.page-1)*listQuery.limit},#{listQuery.limit}" +
            "</script>")
    List<Map<String,Object>> getObstacleList(@Param("listQuery") ListQuery listQuery);


    @Select("<script> select count(id) from obstacle " +
            "<where>" +
            "<if test='listQuery.area != null'> and location like concat('%',#{listQuery.area,jdbcType=VARCHAR},'%') </if>" +
            "<if test='listQuery.type != null'> and type = #{listQuery.type} </if>" +
            "<if test='listQuery.processingStatus != null'> and processing_status = #{listQuery.processingStatus} </if>" +
            "<if test='listQuery.processingStartTime != null and  listQuery.processingEndTime != null' > and processing_time between #{listQuery.processingStartTime} and #{listQuery.processingEndTime} </if>" +
            "<if test='listQuery.discoveryStartTime != null and listQuery.discoveryEndTime != null'> and discovery_time between #{listQuery.discoveryStartTime} and #{listQuery.discoveryEndTime} </if>" +
            "<if test='listQuery.processorId != null'> and processor_id = #{listQuery.processorId}</if>" +
            "</where>" +
            "</script>")
    Integer getObstacleListCount(@Param("listQuery") ListQuery listQuery);

    //TODO 只保留必要的

    @Insert("insert into obstacle (location,type,discovery_time,processing_status,processing_time,processor_id,image_path) " +
            "values (#{record.location},#{record.type},#{record.discoveryTime},#{record.processingStatus},#{record.processingTime},#{record.processorId},#{record.imagePath})")
    int addObstacle(@Param("record") Map<String,Object> newObstacle);

    @Delete("delete from obstacle where id = #{obstacleId}")
    int deleteObstacle(@Param("obstacleId")Integer obstacleId);

//    @Update("update obstacle set " +
//            "location = #{record.location}," +
//            "description = #{record.description}," +
//            "processing_status = #{record.processingStatus}," +
//            "processing_time = #{record.processingTime}," +
//            "processor_id = #{record.processorId} " +
//            "where id = #{record.id}")


//    @Update("<script> update obstacle" +
//            "<set>" +
//            "<if test='record.location != null'> location = #{record.location}, </if>" +
//            "<if test='record.description != null'> description = #{record.description}, </if>" +
//            "<if test='record.processingStatus != null'> processing_status = #{record.processingStatus}, </if>" +
//            "<if test='record.processingTime != null'> processing_time = #{record.processingTime}, </if>" +
//            "<if test='record.processorId != null'> processor_id = #{record.processorId}, </if>" +
//            "</set>" +
//            "where id = #{record.id}; " +
//            "</script>")
//
    @Update("update obstacle set " +
            "location = #{record.location}," +
            "type = #{record.type}," +
            "discovery_time = #{record.discoveryTime}," +
            "processing_status = #{record.processingStatus}," +
            "processing_time = #{record.processingTime}," +
            "processor_id = #{record.processorId}, " +
            "image_path = #{record.imagePath}"+
            "where id = #{record.id}")
    int updateObstacle(@Param("record") Map<String,Object> params);



    @Select("select * from `user` inner join user_obstacle_responsibility " +
            "on user_obstacle_responsibility.user_id = `user`.id " +
            "where user_obstacle_responsibility.obstacle_id = #{obstacleId}")
    List<User> getUserListByObstacleId(@Param("obstacleId") Integer obstacleId);


    @Select("select * from `user` where id not in " +
            "(select user_id from user_obstacle_responsibility where obstacle_id = #{obstacleId})")
    List<User> getUserListByNotObstacleId(Integer obstacleId);


    @Select("select * from obstacle_list " +
            "where obstacle_list.id = #{obstacleId}")
    ObstacleList getObstacleDetail(@Param("obstacleId")Integer obstacleId);


    @Select("select * from obstacle where id = #{obstacleId}")
    Obstacle getObstacle(@Param("obstacleId") Integer obstacleId);

    @Update("update obstacle set " +
            "processing_status = #{record.processingStatus}, " +
            "processing_time = #{record.processingTime}, " +
            "processor_id = #{record.processorId} " +
            "where id = #{record.id}")
    int updateObstacleProcessingStatus(@Param("record")Obstacle obstacle);

    @Select("SELECT DISTINCT location FROM obstacle where location is not null")
    List<String> getLocationOptions();
    @Select("SELECT DISTINCT type  FROM obstacle where type is not null")
    List<String> getTypeOptions();
    @Select("SELECT DISTINCT(obstacle.processor_id) as processorId , user.real_name as realName FROM obstacle " +
            "left join user on user.id = obstacle.processor_id where obstacle.processor_id is not null")
    List<Map<String,Object>> getProcessorOptions();



    @Select("select * from obstacle_list where location like concat('%',#{area},'%')")
    List<ObstacleList> getObstacleListByArea(@Param("area") String areaFullName);

    @Select("select * from obstacle_list where location like concat('%',#{area},'%') and processing_status = 0")
    List<ObstacleList> getUnprocessedObstacleListByArea(String areaFullName);

    @Select("select phone_number as phoneNumber,real_name as realName from user where user.id in (" +
            "select user_id from user_obstacle_responsibility where obstacle_id = #{obstacleId} )")
    List<Object> getResponsibilityByobstacleId(Integer obstacleId);
    @Insert("insert into `user_obstacle_responsibility` (`user_id`,`obstacle_id`) values(" +
            "#{record.userId},#{record.obstacleId}" +
            ") ")
    int assignResponsibility(@Param("record") UserObstacleResponsibility userObstacleResponsibility);


    @Delete("delete from `user_obstacle_responsibility` where " +
            "user_id = #{record.userId} and obstacle_id = #{record.obstacleId}")
    int deleteResponsibility(@Param("record")UserObstacleResponsibility userObstacleResponsibility);


    /**
     *
     * 根据下面的存储过程，获取统计数据
     *         数量    同比    环比
     * 今日发现
     * 本周发现
     * 本月发现
     * 今年发现
     *
     */
    @Select("select num,\n" +
            "       (num - today_count_tb) / today_count_tb * 100 as tb,\n" +
            "       (num - today_count_hb) / today_count_hb * 100 as hb from\n" +
            "(select count(*) as num from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d')) t1,\n" +
            "(select count(*) as today_count_tb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m-%d') = date_format(date_sub(now(), interval 1 year), '%Y-%m-%d')) t2,\n" +
            "(select count(*) as today_count_hb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m-%d') = date_format(date_sub(now(), interval 1 day), '%Y-%m-%d')) t3\n" +
            "union\n" +
            "# 本周发现数量，同比，环比\n" +
            "select num,\n" +
            "       (num - week_count_tb) / week_count_tb * 100 as tb,\n" +
            "       (num - week_count_hb) / week_count_hb * 100 as hb from\n" +
            "(select count(*) as num from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m-%d') >= date_format(date_sub(now(), interval 7 day), '%Y-%m-%d')) t1,\n" +
            "(select count(*) as week_count_tb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m-%d') >= date_format(date_sub(now(), interval 1 year), '%Y-%m-%d')) t2,\n" +
            "(select count(*) as week_count_hb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m-%d') >= date_format(date_sub(now(), interval 7 day), '%Y-%m-%d')) t3\n" +
            "union\n" +
            "# 本月发现数量，同比，环比\n" +
            "select num,\n" +
            "       (num - month_count_tb) / month_count_tb * 100 as tb,\n" +
            "       (num - month_count_hb) / month_count_hb * 100 as hb from\n" +
            "(select count(*) as num from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m') = date_format(now(), '%Y-%m')) t1,\n" +
            "(select count(*) as month_count_tb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m') = date_format(date_sub(now(), interval 1 year), '%Y-%m')) t2,\n" +
            "(select count(*) as month_count_hb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y-%m') = date_format(date_sub(now(), interval 1 month), '%Y-%m')) t3\n" +
            "union\n" +
            "select num,\n" +
            "       (num - year_count_tb) / year_count_tb * 100 as tb,\n" +
            "       (num - year_count_hb) / year_count_hb * 100 as hb from\n" +
            "(select count(*) as num from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y') = date_format(now(), '%Y')) t1,\n" +
            "(select count(*) as year_count_tb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y') = date_format(date_sub(now(), interval 1 year), '%Y')) t2,\n" +
            "(select count(*) as year_count_hb from obstacle where location like CONCAT('%',#{location},'%') and date_format(discovery_time, '%Y') = date_format(date_sub(now(), interval 1 year), '%Y')) t3;")
    List<Map<String,Object>> getObstacleStatistics(@Param("location")String location);
//    @Select(value = "CALL getObstacleStatistics()", resultType = Void)
//    @Select(value = "CALL getOptions()", resultType = Void.class)
//    void executeGetOptions(ResultHandler handler);

    @Select("<script> select type, count(*) as count from obstacle " +
            "<where> " +
            "<if test='begDate != null and endDate != null'> and discovery_time between #{begDate} and #{endDate} </if>" +
            "<if test='location != null'> and location like CONCAT('%',#{location},'%') </if>" +
            "</where>" +
            "group by type" +
            "</script>")
    List<Map<String,Object>> getObstacleTypeByDate(@Param("location")String location,@Param("begDate")String begDate,@Param("endDate")String endDate);


    @Select("(select date_format(discovery_time, '%Y-%m-%d') as date, count(*) as count, 'discovery' as status\n" +
            "from obstacle\n" +
            "where discovery_time > date_sub(now(), interval 1 week)\n" +
            "and location like CONCAT('%',#{location},'%')\n" +
            "group by date\n" +
            "order by date)\n" +
            "union\n" +
            "# 查看一周内每天处理障碍物数量\n" +
            "select date_format(processing_time, '%Y-%m-%d') as date, count(*) as count,'processing' as status\n" +
            "from obstacle\n" +
            "where processing_time > date_sub(now(), interval 1 week)\n" +
            "and location like CONCAT('%',#{location},'%')\n" +
            "group by date\n" +
            "order by date;")
    List<Map<String,Object>> getObstacleCountByDate(@Param("location")String location);


}
