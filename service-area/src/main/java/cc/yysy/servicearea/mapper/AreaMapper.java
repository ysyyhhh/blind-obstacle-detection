package cc.yysy.servicearea.mapper;

import cc.yysy.servicearea.Bean.MyAreaSubscription;
import cc.yysy.utilscommon.entity.Area;
import cc.yysy.utilscommon.entity.AreaList;
import cc.yysy.utilscommon.entity.UserAreaSubscription;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface AreaMapper {


    @Select("select * from area")
    List<Area> getAreaList();

    @Update("update area set " +
            "full_name = #{record.fullName}," +
            "level = #{record.level} " +
            "where id = #{record.id}")
    int updateArea(@Param("record") Area area);

    @Delete("delete from area where id = #{areaId}")
    int deleteArea(@Param("areaId") Integer areaId);

    @Insert("insert into area (`full_name`,`level`) values( " +
            "#{record.fullName},#{record.level})")
    int insertArea(Area area);

    @Insert("insert into `user_area_subscription` (`user_id`,`area_id`) values(${record.userId},${record.areaId})")
    int subscribeArea(@Param("record") UserAreaSubscription userAreaSubscription);

    @Delete("delete from `user_area_subscription` where " +
            "user_id = #{record.userId} and area_id = #{record.areaId}")
    int unSubscribeArea(@Param("record")UserAreaSubscription userAreaSubscription);


    @Select("select distinct(level) from area_list where level is not null")
    List<String> getAreaLevelList();

    @Select("<script> " +
            "select a.*," +
            "       case " +
            "       when uas.user_id is not null then 1 " +
            "       else 0 " +
            "       end as isSubscribed " +
            "from area_list a left outer join (select * from user_area_subscription u where u.user_id = #{listQuery.userId}) uas" +
            "    on a.id = uas.area_id" +
            "<where> " +
            "<if test='listQuery.level != null'> and a.level = #{listQuery.level} </if> " +
            "<if test='listQuery.fullName != null'> and a.full_name like concat('%',#{listQuery.fullName,jdbcType=VARCHAR},'%') </if>" +
            "</where> " +
            "limit ${(listQuery.page-1)*listQuery.limit},#{listQuery.limit}" +
            "</script>")
    List<MyAreaSubscription> getUserSubscribedAreaList(@Param("listQuery") Map<String,Object> map);

    @Select("select count(*) from area_list a left outer join (select * from user_area_subscription u where u.user_id = #{listQuery.userId}) uas" +
            "    on a.id = uas.area_id ")
    int getUserSubscribedAreaListTotal(@Param("listQuery") Map<String,Object> map);

    @Select("select a.*," +
            "       case " +
            "       when uas.user_id is not null then 1 " +
            "       else 0 " +
            "       end as isSubscribed " +
            "from area_list a left outer join (select * from user_area_subscription u where u.user_id = #{userId}) uas" +
            "    on a.id = uas.area_id " +
            "where a.id = #{areaId}")
    MyAreaSubscription getMyAreaSubscriptionByAreaId(@Param("userId")Integer userId, @Param("areaId")Integer areaId);

    @Select("select distinct(full_name) from area_list where full_name is not null")
    List<String> getAreaFullNameList();

    @Insert("insert into area (`full_name`,`level`) values( " +
            "#{record.fullName},#{record.level})")
    int addArea(@Param("record") Area area);




    @Select("select a.* from area a,area b where a.full_name like b.full_name || '%' order by a.full_name;")
    List<Area> getAreaListOrderByFullName();


}
