package com.zyb.mybatis.utils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体类的操作
 * Created by zyb on 2016/8/12.
 */
public class EntityUtil {

    /**
     * 得到表名
     * @param entityClass 实体类
     * @return 根据实体类返回表名
     */
    public static String getTableName(Class<?> entityClass)
    {
        if(entityClass.isAnnotationPresent(Table.class))
        {
            Table table = entityClass.getAnnotation(Table.class);
            if(!table.name().isEmpty())
            {
                return table.name();
            }
            else
            {
                //需要对没有表名的情况下进行处理
            }
        }

        //需要对没有table注解的情况下进行处理
        return null;
    }

    /**
     * 得到类中的属性名以及所对应表的列名
     * @param entityClass 实体类
     * @return 将属性名和列名作为一个Map返回
     */
    public static Map<String, String> getFields(Class<?> entityClass)
    {
        Map<String, String> map = new HashMap<String, String>();

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields)
        {
            if(field.isAnnotationPresent(Column.class))
            {
                Column column = field.getAnnotation(Column.class);
                if(!column.name().isEmpty())
                {
                    map.put(field.getName(), column.name());
                }
                else
                {
                    //待补充，需要对没有列属性的进行操作
                }
            }
        }

        return map;
    }

    public static Map<String, String> getTableId(Class<?> entityClass)
    {
        Map<String, String> map = new HashMap<String, String>();

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields)
        {
            /*
             * 判定是否有ID标签
             */
            if(field.isAnnotationPresent(Id.class))
            {
                /*
                 * 判定是否有column标签，有则得到它的对应表列名
                 */
                if(field.isAnnotationPresent(Column.class))
                {
                    Column column = field.getAnnotation(Column.class);
                    if(!column.name().isEmpty())
                    {
                        map.put("id", field.getName());
                        map.put("tableId", column.name());

                        return map;
                    }
                    else
                    {
                        //待补充，需要对没有列属性的进行操作
                    }
                }
            }
        }

        //没有ID标签的话，默认返回id
        map.put("id", "id");
        map.put("tableId", "id");

        return map;
    }
}
