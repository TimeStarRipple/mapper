package com.zyb.mybatis.dao;

import com.zyb.mybatis.provider.BaseDaoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Dao层通用接口
 * Created by zyb on 2016/8/13.
 */
@CacheNamespace(size = 512)
public interface BaseDao<T, PK> {

    /**
     * 得到所有的对象
     * @param entity
     * @return
     */
    @SelectProvider(type = BaseDaoProvider.class, method = "selectAll")
    public List<T> selectAll(@Param("entity") T entity);

    /**
     * 根据id得到记录
     * @param entity
     * @param id
     * @return
     */
    @SelectProvider(type = BaseDaoProvider.class, method = "selectOne")
    public T selectOne(@Param("entity") T entity, @Param("id") PK id);

    /**
     * 通过实体类添加数据
     * @param entity
     * @return
     */
    @InsertProvider(type = BaseDaoProvider.class, method = "insert")
    public int insert(@Param("entity") T entity);

    /**
     * 根据id更新数据
     * @param entity
     * @param id
     * @return
     */
    @UpdateProvider(type = BaseDaoProvider.class, method = "updateById")
    public int updateById(@Param("entity") T entity, @Param("id") PK id);

    /**
     * 根据id删除数据
     * @param entity
     * @param id
     * @return
     */
    @DeleteProvider(type = BaseDaoProvider.class, method = "deleteById")
    public int deleteById(@Param("entity") T entity, @Param("id") PK id);
}
