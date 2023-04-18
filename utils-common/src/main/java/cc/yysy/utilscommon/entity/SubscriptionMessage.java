package cc.yysy.utilscommon.entity;

import javax.annotation.Generated;

public class SubscriptionMessage {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer obstacleId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Boolean isRead;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getObstacleId() {
        return obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setObstacleId(Integer obstacleId) {
        this.obstacleId = obstacleId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getUserId() {
        return userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Boolean getIsRead() {
        return isRead;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}