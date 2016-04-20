package com.dry.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 数据库辅助类
 * @author jason
 * @date 2016年4月20日 下午10:50:33
 * 
 */
public class DataBaseHelper
{
	private static Logger LOG = LoggerFactory.getLogger(DataBaseHelper.class);
	private static final ThreadLocal<Connection> CONN_HOLDER;
	private static final QueryRunner QUERY_RUN;
	private static final BasicDataSource DATA_SOURCE;
	private static final String DRIVER;
	private static final String URL;
	private static final String USER;
	private static final String PWD;

	static
	{
		Properties conf = PropsUtil.loadProps("config.properties");
		DRIVER = conf.getProperty("jdbc.driver");
		URL = conf.getProperty("jdbc.url");
		USER = conf.getProperty("jdbc.username");
		PWD = conf.getProperty("jdbc.password");
		CONN_HOLDER = new ThreadLocal<>();
		QUERY_RUN = new QueryRunner();
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(DRIVER);
		DATA_SOURCE.setUrl(URL);
		DATA_SOURCE.setUsername(USER);
		DATA_SOURCE.setPassword(PWD);
		// TODO 还可以设置连接池其它属性
	}

	public static Connection getConnection()
	{
		Connection conn = CONN_HOLDER.get();
		if (null == conn)
		{
			try
			{
				conn = DATA_SOURCE.getConnection();
			}
			catch (SQLException e)
			{
				LOG.error("get connection failuer" + e);
				throw new RuntimeException(e);
			}
			finally
			{
				CONN_HOLDER.set(conn);
			}
		}
		return conn;
	}

	/**
	 * 查询实体结果集
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params)
	{
		List<T> entityList = null;
		try
		{
			entityList = QUERY_RUN.query(getConnection(), sql, new BeanListHandler<T>(entityClass), params);
		}
		catch (SQLException e)
		{
			LOG.error("query [" + entityClass + "] entityList failure", e);
		}
		return entityList;
	}
	
	

}
