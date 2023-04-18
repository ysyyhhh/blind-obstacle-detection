package cc.yysy.utilscommon.entity;

import javax.annotation.Generated;

public class UserAreaSubscription {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer areaId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getUserId() {
        return userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserId(Integer userId) {
        this.userId = userId;
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