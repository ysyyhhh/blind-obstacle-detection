package cc.yysy.utilscommon.entity;

import javax.annotation.Generated;

public class ObstacleAreaOwnership {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer obstacleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer areaId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getObstacleId() {
        return obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setObstacleId(Integer obstacleId) {
        this.obstacleId = obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getAreaId() {
        return areaId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}