package cc.yysy.utilscommon.entity;

import javax.annotation.Generated;

public class DataSourceList {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String type;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer runtime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String area;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long obstacleCount;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getType() {
        return type;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setType(String type) {
        this.type = type;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getRuntime() {
        return runtime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getArea() {
        return area;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setArea(String area) {
        this.area = area;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getObstacleCount() {
        return obstacleCount;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setObstacleCount(Long obstacleCount) {
        this.obstacleCount = obstacleCount;
    }
}