package cc.yysy.servicedatasource.service.impl;

import cc.yysy.servicedatasource.mapper.DataSourceMapper;
import cc.yysy.servicedatasource.service.DataSourceService;
import cc.yysy.utilscommon.entity.DataSource;
import cc.yysy.utilscommon.result.Result;
import cc.yysy.utilscommon.utils.JWTUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataSourceInfoServiceImpl implements DataSourceService {

    @Resource
    DataSourceMapper dataSourceMapper;

    public List<DataSource> dataSourceList() {
        return dataSourceMapper.getDataSourceList();
    }
    public Result getDataSourceList() {
        return Result.success(dataSourceMapper.getDataSourceList());
    }

    public Result addDataSource(DataSource dataSource) {
        return Result.resultDB(dataSourceMapper.addDataSource(dataSource));
    }

    public Result deleteDataSource(Integer dataSourceId) {
        return Result.resultDB(dataSourceMapper.deleteDataSource(dataSourceId));
    }

    public Result updateDataSource(DataSource dataSource) {
        return Result.resultDB(dataSourceMapper.updateDataSource(dataSource));
    }

    public Result getDataSourceTokenById(Integer dataSourceId) {
        return Result.success(JWTUtils.getDatasourceToken(dataSourceId));
    }

}
