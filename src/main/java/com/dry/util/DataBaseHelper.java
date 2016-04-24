package com.dry.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
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
	
	/**
	 * 得到数据库连接的方法
	 * @return
	 */
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
			throw new RuntimeException(e);
		}
		return entityList;
	}
	
	/**
	 * 查询单个对象
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params)
	{
		T entity = null;
		try
		{
			entity = QUERY_RUN.query(getConnection(), sql, new BeanHandler<>(entityClass), params);
		}
		catch (SQLException e)
		{
			LOG.error("query [" + entityClass + "] entity failure", e);
			throw new RuntimeException(e);
		}
		return entity;
	}

	/**
	 * 联表查询返回List,List中保存为对应的列名与列值
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> executeQuery(String sql, Object... params)
	{
		List<Map<String, Object>> result = null;
		try
		{
			result = QUERY_RUN.query(getConnection(), sql, new MapListHandler(), params);
		}
		catch (SQLException e)
		{
			LOG.error("executeQuery [" + sql + "] failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * 执行update,delete,insert类型的sql语句返回受影响的行数
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql, Object... params)
	{
		int rows = 0;
		try
		{
			rows = QUERY_RUN.update(getConnection(), sql, params);
		}
		catch (SQLException e)
		{
			LOG.error("executeUpdate [" + sql + "] failure", e);
			throw new RuntimeException(e);
		}

		return rows;
	}

	/**
	 * 添加一条数据
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap)
	{
		if (CollectionUtil.isEmpty(fieldMap))
		{
			LOG.error("cat not insert entityMap fieldMap is empty");
			return false;
		}
		String sql = "insert into " + getTableName(entityClass);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for (String fieldName : fieldMap.keySet())
		{
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}
		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");

		sql += columns + " values " + values;

		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}
	
	/**
	 * 更新表中数据
	 * @param entityClass
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap)
	{
		if (CollectionUtil.isEmpty(fieldMap))
		{
			LOG.error("cat not update entityMap fieldMap is empty");
			return false;
		}
		String sql = "update " + getTableName(entityClass) + "set ";
		StringBuilder columns = new StringBuilder("(");
		for (String fieldName : fieldMap.keySet())
		{
			columns.append(fieldName).append("=?, ");
		}
		sql += columns.substring(0, columns.lastIndexOf(", ")) + "where id=" + id;
		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();
		return executeUpdate(sql, params) == 1;
	}
	
	/**
	 * 根据id与实例class删除记录
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass, long id)
	{
		String sql = "delete from " + getTableName(entityClass) + " where id=" + id;
		return executeUpdate(sql, id) == 1;
	}

	/**
	 * 根据class类得到对应的数据库表名
	 * 
	 * @param entityClass
	 * @return
	 */
	private static String getTableName(Class<?> entityClass)
	{
		return entityClass.getSimpleName();
	}

}