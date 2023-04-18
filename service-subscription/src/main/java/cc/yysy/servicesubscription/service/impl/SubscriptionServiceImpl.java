package cc.yysy.servicesubscription.service.impl;

import cc.yysy.servicesubscription.mapper.SubscriptionMapper;
import cc.yysy.servicesubscription.service.SubscriptionService;
import cc.yysy.utilscommon.entity.SubscriptionMessage;
import cc.yysy.utilscommon.entity.UserAreaSubscription;
import cc.yysy.utilscommon.result.Result;
import org.springframework.stereotype.Service;
import rx.Subscription;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Resource
    SubscriptionMapper subscriptionMapper;


    public Result getUnReadMessageList(Integer id) {
        return Result.success(subscriptionMapper.getUnReadMessageList(id));
    }

    public Result addObstacleToArea(String areaFullName, Integer obstacleId) {

        List<Integer> userIdList = subscriptionMapper.getUserListByAreaFullName(areaFullName);

        for(Integer userId : userIdList){
            SubscriptionMessage subscriptionMessage = new SubscriptionMessage();
            subscriptionMessage.setUserId(userId);
            subscriptionMessage.setObstacleId(obstacleId);
            subscriptionMapper.addMessage(subscriptionMessage);
        }
        return Result.success("推送通知成功");
    }


    public Result addMessage(SubscriptionMessage subscriptionMessage) {
        Result result;
        try {
            result = Result.resultDB(subscriptionMapper.addMessage(subscriptionMessage));
        }catch (Exception e){
            return Result.error("添加失败");
        }
        return result;
    }

}
