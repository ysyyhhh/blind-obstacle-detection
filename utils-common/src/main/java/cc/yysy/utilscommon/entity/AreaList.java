package cc.yysy.utilscommon.entity;

import javax.annotation.Generated;

public class AreaList {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String fullName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer level;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long obstacleCount;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long unprocessedObstacleCount;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getFullName() {
        return fullName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getLevel() {
        return level;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getObstacleCount() {
        return obstacleCount;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setObstacleCount(Long obstacleCount) {
        this.obstacleCount = obstacleCount;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getUnprocessedObstacleCount() {
        return unprocessedObstacleCount;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUnprocessedObstacleCount(Long unprocessedObstacleCount) {
        this.unprocessedObstacleCount = unprocessedObstacleCount;
    }
}