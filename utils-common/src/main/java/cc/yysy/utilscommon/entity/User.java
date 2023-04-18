package cc.yysy.utilscommon.entity;

import javax.annotation.Generated;

public class User {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String phoneNumber;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String username;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String realName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String password;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String userType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUsername() {
        return username;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUsername(String username) {
        this.username = username;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRealName() {
        return realName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPassword() {
        return password;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPassword(String password) {
        this.password = password;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUserType() {
        return userType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserType(String userType) {
        this.userType = userType;
    }
}