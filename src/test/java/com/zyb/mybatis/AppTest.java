package com.zyb.mybatis;

import com.zyb.mybatis.dao.UserDao;
import com.zyb.mybatis.entity.User;
import com.zyb.mybatis.utils.MybatisUtils;
import junit.framework.TestCase;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    @org.junit.Test
    public void testDelete()
    {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);

        int result = userDao.deleteById(new User(), 1);

        sqlSession.commit();
        sqlSession.close();

        System.out.println(result);
    }

    @org.junit.Test
    public void testInsert()
    {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);

        User user = new User();
        user.setUsername("dsklfjl")
                .setPassword("234234234234");
        user.setPhone("242424242");

        int result = userDao.insert(user);

        sqlSession.commit();
        sqlSession.close();

        System.out.println(result);
    }

    @org.junit.Test
    public void testUpdate()
    {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        User user = new User();
        user.setUserId(4);
        user.setUsername("bigbin");
        user.setEmail("abccdd");

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        int result = userDao.updateById(user, 3);
        System.out.println(result);

        sqlSession.commit();
        sqlSession.close();

    }

    @org.junit.Test
    public void testSelectOne()
    {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.selectOne(new User(), 3);
        System.out.println(user);

        sqlSession.close();
    }

    @org.junit.Test
    public void testSelectAll()
    {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> list = userDao.selectAll(new User());
        for (User user : list)
        {
            System.out.println(user);
        }

        sqlSession.close();
    }
}
