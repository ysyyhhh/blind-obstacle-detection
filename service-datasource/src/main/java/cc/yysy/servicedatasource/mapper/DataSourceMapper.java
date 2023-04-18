package cc.yysy.servicedatasource.mapper;

import cc.yysy.utilscommon.entity.DataSource;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DataSourceMapper {

    @Select("select * from data_source")
    List<DataSource> getDataSourceList();

    @Insert("insert into data_source (type,description,status) values (#{record.type},#{record.description},#{record.status})")
    int addDataSource(@Param("record") DataSource dataSource);

    @Delete("delete from data_source where id = #{dataSourceId}")
    int deleteDataSource(@Param("dataSourceId") Integer dataSourceId);

    @Update("update data_source set type = #{record.type},description = #{record.description},status = #{record.status} where id = #{record.id}")
    int updateDataSource(@Param("record") DataSource dataSource);
}
