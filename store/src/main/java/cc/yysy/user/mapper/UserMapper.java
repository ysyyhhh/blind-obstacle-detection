package cc.yysy.serviceuser.mapper;

import static cc.yysy.serviceuser.mapper.UserDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import cc.yysy.serviceuser.entity.User;
import java.util.List;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.DeleteDSL;
import org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.MyBatis3SelectModelAdapter;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectDSL;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

@Mapper
public interface UserMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<User> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserResult")
    User selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="phone_number", property="phoneNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="real_name", property="realName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR)
    })
    List<User> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<Long>> countByExample() {
        return SelectDSL.selectWithMapper(this::count, SqlBuilder.count())
                .from(user);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default DeleteDSL<MyBatis3DeleteModelAdapter<Integer>> deleteByExample() {
        return DeleteDSL.deleteFromWithMapper(this::delete, user);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return DeleteDSL.deleteFromWithMapper(this::delete, user)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(User record) {
        return insert(SqlBuilder.insert(record)
                .into(user)
                .map(id).toProperty("id")
                .map(phoneNumber).toProperty("phoneNumber")
                .map(username).toProperty("username")
                .map(realName).toProperty("realName")
                .map(password).toProperty("password")
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(User record) {
        return insert(SqlBuilder.insert(record)
                .into(user)
                .map(id).toPropertyWhenPresent("id", record::getId)
                .map(phoneNumber).toPropertyWhenPresent("phoneNumber", record::getPhoneNumber)
                .map(username).toPropertyWhenPresent("username", record::getUsername)
                .map(realName).toPropertyWhenPresent("realName", record::getRealName)
                .map(password).toPropertyWhenPresent("password", record::getPassword)
                .build()
                .render(RenderingStrategy.MYBATIS3));
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<User>>> selectByExample() {
        return SelectDSL.selectWithMapper(this::selectMany, id, phoneNumber, username, realName, password)
                .from(user);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default QueryExpressionDSL<MyBatis3SelectModelAdapter<List<User>>> selectDistinctByExample() {
        return SelectDSL.selectDistinctWithMapper(this::selectMany, id, phoneNumber, username, realName, password)
                .from(user);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default User selectByPrimaryKey(Integer id_) {
        return SelectDSL.selectWithMapper(this::selectOne, id, phoneNumber, username, realName, password)
                .from(user)
                .where(id, isEqualTo(id_))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExample(User record) {
        return UpdateDSL.updateWithMapper(this::update, user)
                .set(id).equalTo(record::getId)
                .set(phoneNumber).equalTo(record::getPhoneNumber)
                .set(username).equalTo(record::getUsername)
                .set(realName).equalTo(record::getRealName)
                .set(password).equalTo(record::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default UpdateDSL<MyBatis3UpdateModelAdapter<Integer>> updateByExampleSelective(User record) {
        return UpdateDSL.updateWithMapper(this::update, user)
                .set(id).equalToWhenPresent(record::getId)
                .set(phoneNumber).equalToWhenPresent(record::getPhoneNumber)
                .set(username).equalToWhenPresent(record::getUsername)
                .set(realName).equalToWhenPresent(record::getRealName)
                .set(password).equalToWhenPresent(record::getPassword);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(User record) {
        return UpdateDSL.updateWithMapper(this::update, user)
                .set(phoneNumber).equalTo(record::getPhoneNumber)
                .set(username).equalTo(record::getUsername)
                .set(realName).equalTo(record::getRealName)
                .set(password).equalTo(record::getPassword)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(User record) {
        return UpdateDSL.updateWithMapper(this::update, user)
                .set(phoneNumber).equalToWhenPresent(record::getPhoneNumber)
                .set(username).equalToWhenPresent(record::getUsername)
                .set(realName).equalToWhenPresent(record::getRealName)
                .set(password).equalToWhenPresent(record::getPassword)
                .where(id, isEqualTo(record::getId))
                .build()
                .execute();
    }
}