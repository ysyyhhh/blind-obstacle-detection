package cc.yysy.utilscommon.entity;

import java.util.Date;
import javax.annotation.Generated;

public class ObstacleList {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String type;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String location;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date discoveryTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date processingTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer processingStatus;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer processorId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String imagePath;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Double latitude;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Double longitude;

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
    public String getLocation() {
        return location;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLocation(String location) {
        this.location = location;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getDiscoveryTime() {
        return discoveryTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDiscoveryTime(Date discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getProcessingTime() {
        return processingTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProcessingTime(Date processingTime) {
        this.processingTime = processingTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getProcessingStatus() {
        return processingStatus;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProcessingStatus(Integer processingStatus) {
        this.processingStatus = processingStatus;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getProcessorId() {
        return processorId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProcessorId(Integer processorId) {
        this.processorId = processorId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getImagePath() {
        return imagePath;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Double getLatitude() {
        return latitude;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Double getLongitude() {
        return longitude;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}