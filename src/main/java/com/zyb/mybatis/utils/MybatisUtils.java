package com.zyb.mybatis.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
* mybatis的工具类
* @author zyb
* @version 1.0
* @date 2016年8月5日 上午11:23:25
*/
public class MybatisUtils
{
	//这个路径是相对于资源文件夹的路径
	private static String resource = "config/mybatis-config.xml";
	
	/*
	 * 实现单例 
	 */
	private static SqlSessionFactory factory = getSessionFactory();	
	private MybatisUtils(){}
	private static SqlSessionFactory getSessionFactory()
	{
		if(factory == null)
		{
			Reader reader = null;	
			try
			{
				//将配置文件转换成输入流
				reader = Resources.getResourceAsReader(resource);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			/*
			 * 通过文件输入流创建SqlSessionFactory，并通过工厂类创建sqlSession
			 * ，由于SqlSessionFactory对象创建之后一直会存在，所以不建议多次创建
			 * ，最好采用单例创建一次就好，注意，一个数据库一个工厂。
			 */
			factory = new SqlSessionFactoryBuilder().build(reader);
		}
		
		return factory;
	}
	
	public static SqlSession getSqlSession()
	{
		SqlSession sqlSession = null;		

		//SqlSession是非线程安全的，所以不能共享资源，不建议作为类的属性，最好和http请求一样
		//，请求来了打开，结束后关闭，一般放在finally里面
		sqlSession = factory.openSession();
		
		return sqlSession;
	}
}
