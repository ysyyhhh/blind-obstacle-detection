package cc.yysy.utilscommon.entity;

import java.util.Date;
import javax.annotation.Generated;

public class ObstacleRawData {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String dataFilePath;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer dataSourceId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer obstacleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date discoveryTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getDataFilePath() {
        return dataFilePath;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getDataSourceId() {
        return dataSourceId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getObstacleId() {
        return obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setObstacleId(Integer obstacleId) {
        this.obstacleId = obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getDiscoveryTime() {
        return discoveryTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDiscoveryTime(Date discoveryTime) {
        this.discoveryTime = discoveryTime;
    }
}