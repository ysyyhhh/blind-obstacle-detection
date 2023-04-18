package cc.yysy.servicearea.service.impl;

import cc.yysy.servicearea.Bean.MyAreaSubscription;
import cc.yysy.servicearea.mapper.AreaMapper;
import cc.yysy.servicearea.service.AreaService;
import cc.yysy.utilscommon.api.BaiDuApi;
import cc.yysy.utilscommon.entity.Area;
import cc.yysy.utilscommon.entity.UserAreaSubscription;
import cc.yysy.utilscommon.result.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.logging.Logger;

@Service
public class AreaServiceImpl implements AreaService {
    static Logger logger = Logger.getLogger("AreaServiceImpl log");

    @Resource
    AreaMapper areaMapper;
    static final String AREA_KEY = "AREA_KAY";
    static Set<String> areaSet = new HashSet<>();

    /**
     * 获取区域树,现在要加入该树中，但不确定它是否有后代
     * preTree:上一级的树
     *  children:[...,area]
     * @return
     */
    private int addToAreaTree(List<JSONObject> preTree,List<Area> areaList,int i){
        Area area = areaList.get(i);
        JSONObject areaJson = new JSONObject();
        areaJson.put("id",area.getId());
//        logger.info(area.getFullName());
//        logger.info(area.getLevel()+"");
        String name[] = area.getFullName().split("-");
        String label = name[area.getLevel()];
        for(int j = area.getLevel()+1;j < name.length;j++){
            label = label + "-" + name[j];
        }
        areaJson.put("label",label);
        areaJson.put("level",area.getLevel());
        List<JSONObject> children = new ArrayList<>();
        i++;
        while(i < areaList.size()){
            Area nextArea = areaList.get(i);
            if(nextArea.getLevel() == area.getLevel() + 1) {
                //有后代
                i = addToAreaTree(children, areaList, i);
            }else if(nextArea.getLevel() == area.getLevel()) {
                //平行
                i = addToAreaTree(preTree, areaList, i);
            }else{
                //否则，直接添加并返回即可。
                break;
            }
        }
        areaJson.put("children",children);
        preTree.add(areaJson);
        return i;
    }
    public Result getAreaTree() {
        List<Area> areaList = areaMapper.getAreaListOrderByFullName();
        List<JSONObject> areaTreeRoot = new ArrayList<>();
        addToAreaTree(areaTreeRoot,areaList,0);
//        logger.info(areaTreeRoot.toString());
        return Result.success(areaTreeRoot);
    }

    public Result getLocation(String address) {
        String gps = BaiDuApi.getLocation(address);
        if(gps == null){
            return Result.error("获取位置失败");
        }
        return Result.success(gps);
    }



    /**
     * 初始化
     */
    @Component
    public class InitCommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            System.out.println("初始化：InitCommandLineRunner");
            initReadAreaList();
        }
    }
    void initReadAreaList(){
        areaSet.clear();
        List<Area> areaList = areaMapper.getAreaList();
        for(Area area : areaList){
            String fullName = area.getFullName();
//            logger.info(fullName);
            areaSet.add(fullName);
        }
    }
    public Result getAreaList() {
        return Result.success(areaMapper.getAreaList());
    }
    public Result updateArea(Area area) {
        return Result.resultDB(areaMapper.updateArea(area));
    }
    public Result deleteArea(Integer areaId) {
        return Result.resultDB(areaMapper.deleteArea(areaId));
    }
    public Result insertArea(Area area) {
        return Result.resultDB(areaMapper.insertArea(area));
    }
    public Result subscribeArea(UserAreaSubscription userAreaSubscription) {
        return Result.resultDB(areaMapper.subscribeArea(userAreaSubscription));
    }
    public Result unSubscribeArea(UserAreaSubscription userAreaSubscription) {
        return Result.resultDB(areaMapper.unSubscribeArea(userAreaSubscription));
    }
    public Result getUserSubscribedAreaList(Map<String,Object> params) {
        List<MyAreaSubscription> list = areaMapper.getUserSubscribedAreaList(params);
        Map<String,Object> result = new HashMap<>();
        result.put("list",list);
        result.put("total",areaMapper.getUserSubscribedAreaListTotal(params));
        return Result.success(result);
    }

    public Result getMyAreaSubscriptionByAreaId(Integer userId, Integer areaId) {
        return Result.success(areaMapper.getMyAreaSubscriptionByAreaId(userId,areaId));
    }

    public Result getOptions() {
        List<String> level = areaMapper.getAreaLevelList();
        List<String> fullName = areaMapper.getAreaFullNameList();
        Map<String,Object> result = new HashMap<>();
        result.put("level",level);
        result.put("fullName",fullName);
        return Result.success(result);
    }

    public Result addAreaByGPS(Double latitude, Double longitude) {
        Map<String,Object> map = BaiDuApi.reverseGeocoding(latitude,longitude);
        String resultStr = (String) map.get("resultStr");
        Integer level = (Integer) map.get("level");
        // resultStr.split("-");
        String[] resultList = resultStr.split("-");
        String fullName = resultList[0];
        Area area = new Area();
        area.setFullName(fullName);
        area.setLevel(0);
        if(!areaSet.contains(area.getFullName())){
            areaSet.add(area.getFullName());
            areaMapper.addArea(area);
        }

        for(int i = 1;i <= level;i++){
            fullName = fullName + "-" + resultList[i];
            area.setFullName(fullName);
            area.setLevel(i);
            if(!areaSet.contains(area.getFullName())){
                areaSet.add(area.getFullName());
                areaMapper.addArea(area);
            }
        }
        return Result.success(fullName);
    }


}
