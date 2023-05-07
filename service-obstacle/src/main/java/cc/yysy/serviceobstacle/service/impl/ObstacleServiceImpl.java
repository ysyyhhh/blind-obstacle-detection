package cc.yysy.serviceobstacle.service.impl;

import cc.yysy.serviceobstacle.Bean.ListQuery;
import cc.yysy.serviceobstacle.mapper.ObstacleMapper;
import cc.yysy.serviceobstacle.service.ObstacleService;
import cc.yysy.utilscommon.entity.Obstacle;
import cc.yysy.utilscommon.entity.UserObstacleResponsibility;
import cc.yysy.utilscommon.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ObstacleServiceImpl implements ObstacleService {
    static Logger logger = Logger.getLogger("ObstacleServiceImpl log");
    @Resource
    ObstacleMapper obstacleMapper;

    public Obstacle getObstacle(Integer obstacleId) {
        return obstacleMapper.getObstacle(obstacleId);
    }
    public Result dealObstacle(Integer userId, Integer obstacleId) {
        Date nowDate = new Date();
        Obstacle obstacle = new Obstacle();
        obstacle.setProcessorId(userId);
        obstacle.setId(obstacleId);
        obstacle.setProcessingTime(nowDate);
        obstacle.setProcessingStatus(1);
        return Result.resultDB( obstacleMapper.dealObstacle(obstacle));
    }

    public Result getObstaclesByFullName(String fullName) {
        return Result.success( obstacleMapper.getObstaclesByFullName(fullName));
    }

    public Result addObstacle(Map<String,Object> newObstacle) {
        return Result.resultDB(obstacleMapper.addObstacle(newObstacle));
    }

    public Result deleteObstacle(Integer obstacleId) {
        return Result.resultDB(obstacleMapper.deleteObstacle(obstacleId));
    }

    public Result updateObstacle( Map<String,Object> params) {

        return Result.resultDB(obstacleMapper.updateObstacle(params));
    }

    /**
     * 分配用户职责
     * @param userObstacleResponsibility
     * @return
     */
    public Result assignResponsibility(UserObstacleResponsibility userObstacleResponsibility) {
        return Result.resultDB(obstacleMapper.assignResponsibility(userObstacleResponsibility));
    }
    public Result deleteResponsibility(UserObstacleResponsibility userObstacleResponsibility){
        return Result.resultDB(obstacleMapper.deleteResponsibility(userObstacleResponsibility));

    }

    public Result getUserListByObstacleId(Integer obstacleId) {
        return Result.success(obstacleMapper.getUserListByObstacleId(obstacleId));
    }

    public Result getObstacleDetail(Integer obstacleId) {
        return Result.success(obstacleMapper.getObstacleDetail(obstacleId));
    }

    public Result getUserListByNotObstacleId(Integer obstacleId) {
        return Result.success(obstacleMapper.getUserListByNotObstacleId(obstacleId));
    }

    /**
     listQuery: {
     page: 1,
     limit: 20,
     area: undefined,
     type: undefined,
     processingStatus: undefined,
     processingStartTime: undefined,
     processingEndTime: undefined,
     discoveryStartTime: undefined,
     discoveryEndTime: undefined,
     processorId: undefined
     },
     return list,total
     **/
    public Result getObstacleList(ListQuery listQuery) {
        Map<String,Object> map = new HashMap<>();
        map.put("list",obstacleMapper.getObstacleList(listQuery));
        map.put("total",obstacleMapper.getObstacleListCount(listQuery));
        return Result.success(map);
    }

    public Result updateObstacleProcessingStatus(Integer userId, Obstacle obstacle, Integer newStatus) {
        if(newStatus == 1){
            obstacle.setProcessingStatus(1);
            obstacle.setProcessingTime(new Date());
            obstacle.setProcessorId(userId);
        }else{
            obstacle.setProcessingStatus(0);
            obstacle.setProcessingTime(null);
            obstacle.setProcessorId(null);
        }
        return Result.resultDB(obstacleMapper.updateObstacleProcessingStatus(obstacle));
    }

    public Result getOptions() {
        List<String> locationOptions = obstacleMapper.getLocationOptions();
        List<String> typeOptions = obstacleMapper.getTypeOptions();
        List<Map<String,Object>> processorOptions = obstacleMapper.getProcessorOptions();
        for (Map<String, Object> processorOption : processorOptions) {
            if(processorOption == null){
                processorOption = new HashMap<>();
            }
            if(!processorOption.containsKey("processorId") ){
                processorOption.put("processorId","");
                processorOption.put("realName","未处理");
            }
            if(!processorOption.containsKey("realName")){
                processorOption.put("realName","未实名");
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("locationOptions",locationOptions);
        map.put("typeOptions",typeOptions);
        map.put("processorOptions",processorOptions);
        logger.info(map.toString());
        return Result.success(map);
//        return Result.success("");
    }

    public Result getObstacleListByArea(String areaFullName) {
        return Result.success(obstacleMapper.getObstacleListByArea(areaFullName));
    }

    public Result getResponsibilityByobstacleId(Integer obstacleId) {
        return Result.success(obstacleMapper.getResponsibilityByobstacleId(obstacleId));
    }

    public Result getObstacleStatistics(String location) {
        return Result.success(obstacleMapper.getObstacleStatistics(location));
    }

    public Result getObstacleTypeByDate(String location,String begDate,String endDate){
        return Result.success(obstacleMapper.getObstacleTypeByDate(location,begDate,endDate));
    }

    public Result getObstacleCountByDate(String location){
        return Result.success(obstacleMapper.getObstacleCountByDate(location));
    }

    public Result getUnprocessedObstacleListByArea(String areaFullName) {
        return Result.success(obstacleMapper.getUnprocessedObstacleListByArea(areaFullName));
    }
}
