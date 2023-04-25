package cc.yysy.servicesubscription.mapper;

import cc.yysy.utilscommon.entity.MessageList;
import cc.yysy.utilscommon.entity.SubscriptionMessage;
import cc.yysy.utilscommon.entity.UserAreaSubscription;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SubscriptionMapper {


    @Select("select * from message_list where user_id = #{userId} and is_read = 0")
    List<MessageList> getUnReadMessageList(@Param("userId") Integer userId);


    @Select("select user_id from user_area_subscription where area_id in (" +
            "select area_id from area where area.full_name = #{areaFullName}" +
            ")")
    List<Integer> getUserListByAreaFullName(@Param("areaFullName")String areaFullName);

    @Insert("Insert into subscription_message (obstacle_id,user_id) values (#{record.obstacleId},#{record.userId})")
    int addMessage(@Param("record") SubscriptionMessage subscriptionMessage);

    @Delete("delete from subscription_message where obstacle_id = #{obstacleId} and user_id = #{userId}")
    int readMessage(Integer obstacleId, Integer userId);
}
