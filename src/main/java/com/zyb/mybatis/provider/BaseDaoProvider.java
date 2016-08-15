package com.zyb.mybatis.provider;

import com.zyb.mybatis.utils.EntityUtil;

import java.lang.reflect.Field;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by zyb on 2016/8/13.
 */
public class BaseDaoProvider<T, PK> {

    /**
     * 通过id删除记录
     * @param map
     * @return
     */
    public String deleteById(Map<String, Object> map)
    {
        PK id = (PK) map.get("id");
        T entity = (T) map.get("entity");

        String tableName = EntityUtil.getTableName(entity.getClass());
        String tableId = EntityUtil.getTableId(entity.getClass()).get("tableId");

        BEGIN();
        DELETE_FROM(tableName);
        WHERE(tableId + " = " + id);

        return SQL();
    }

    /**
     * 插入数据
     * @param map
     * @return
     * @throws IllegalAccessException
     */
    public String insert(Map<String, Object> map) throws IllegalAccessException {
        T entity = (T) map.get("entity");

        Field[] fields = entity.getClass().getDeclaredFields();
        String tableName = EntityUtil.getTableName(entity.getClass());
        Map<String, String> names = EntityUtil.getFields(entity.getClass());

        BEGIN();
        INSERT_INTO(tableName);

        /*
         * 插入属性，这里只插入值不为空的属性，并且在包含有column注解的属性
         */
        for (Field field:fields)
        {
            //私有属性必须写上，才可以访问
            field.setAccessible(true);

            if(field.get(entity) != null && !"".equals(field.get(entity).toString()))
            {
                if(names.containsKey(field.getName()))
                {
                    VALUES(names.get(field.getName()), "#{entity." + field.getName() + "}");
                }
            }
        }

        return SQL();
    }

    /**
     * 通过ID更新数据
     * @param map
     * @return
     */
    public String updateById(Map<String, Object> map) throws IllegalAccessException {
        T entity = (T) map.get("entity");
        PK id = (PK) map.get("id");

        String tableName = EntityUtil.getTableName(entity.getClass());

        /*
         * 获取属性以及有column标注的列
         */
        Field[] fields = entity.getClass().getDeclaredFields();
        Map<String, String> names = EntityUtil.getFields(entity.getClass());

        /*
         * 获取表id名和属性id名
         */
        Map<String, String> idMap = EntityUtil.getTableId(entity.getClass());
        String idStr = idMap.get("id");
        String tableId = idMap.get("tableId");

        BEGIN();
        UPDATE(tableName);

        /*
         * 更新属性拼接
         */
        for(Field field : fields)
        {
            //私有属性必须写上，才可以访问
            field.setAccessible(true);

            if(field.get(entity) != null && !"".equals(field.get(entity)))
            {
                //不对id属性进行更新
                if(!field.getName().equals(idStr) && names.containsKey(field.getName()))
                {
                    SET(names.get(field.getName()) + " = #{entity." + field.getName() + "}");
                }
            }
        }

        WHERE(tableId + " = " + id);

        return SQL();
    }

    /**
     *
     * @param map
     * @return
     */
    public String selectOne(Map<String, Object> map)
    {
        PK id = (PK) map.get("id");
        T entity = (T) map.get("entity");

        String tableName = EntityUtil.getTableName(entity.getClass());
        String tableId = EntityUtil.getTableId(entity.getClass()).get("tableId");
        Map<String, String> names = EntityUtil.getFields(entity.getClass());

        StringBuilder select = new StringBuilder("");
        for (String name : names.keySet())
        {
            select.append(names.get(name) + " as " + name);
            //每一个属性后面加一个“,”
            select.append(",");
        }

        System.out.println(select.toString().substring(0, select.length() - 1));

        BEGIN();
        SELECT(select.toString().substring(0, select.length() - 1));
        FROM(tableName);

        WHERE(tableId + " = " + id);

        return SQL();
    }

    /**
     *
     * @param map
     * @return
     */
    public String selectAll(Map<String, Object> map)
    {
        T entity = (T) map.get("entity");

        String tableName = EntityUtil.getTableName(entity.getClass());
        Map<String, String> names = EntityUtil.getFields(entity.getClass());

        StringBuilder select = new StringBuilder("");
        for (String name : names.keySet())
        {
            select.append(names.get(name) + " as " + name);
            //每一个属性后面加一个“,”
            select.append(",");
        }

        System.out.println(select.toString().substring(0, select.length() - 1));

        BEGIN();
        SELECT(select.toString().substring(0, select.length() - 1));
        FROM(tableName);

        return SQL();
    }
}
