package cc.yysy.utilscommon.entity;

import javax.annotation.Generated;

public class ObstacleGps {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer obstacleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Double longitude;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Double latitude;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getObstacleId() {
        return obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setObstacleId(Integer obstacleId) {
        this.obstacleId = obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Double getLongitude() {
        return longitude;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Double getLatitude() {
        return latitude;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}