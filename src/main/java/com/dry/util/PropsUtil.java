package com.dry.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 配置文件读取类
 * @author jason
 * @date 2016年4月19日 下午10:41:00
 * 
 */
public class PropsUtil
{
	private static final Logger LOG = LoggerFactory.getLogger(PropsUtil.class);

	public static Properties loadProps(String fileName)
	{
		Properties props = null;
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName))
		{
			if (null == in)
			{
				throw new FileNotFoundException(fileName + "File not found");
			}
			props = new Properties();
			props.load(in);
		}
		catch (IOException e)
		{
			LOG.error("load propertis file failure " + e);
		}
		return props;
	}

	public static String getString(Properties props, String key)
	{
		return getString(props, key, "");
	}

	public static String getString(Properties props, String key, String defaultValue)
	{
		String value = defaultValue;
		if (props.containsKey(key))
		{
			value = props.getProperty(key);
		}
		return value;
	}
	
	public static int getInt(Properties props, String key, int defaultValue)
	{
		int value = defaultValue;
		if (props.containsKey(key))
		{
			value = CastUtil.castInt(props.getProperty(key));
		}
		return value;
	}
	
	public static long getLong(Properties props, String key, long defaultValue)
	{
		long value = defaultValue;
		if (props.containsKey(key))
		{
			value = CastUtil.castLong(props.getProperty(key));
		}
		return value;
	}
	
	public static boolean getBoolean(Properties props, String key, boolean defaultValue)
	{
		boolean value = defaultValue;
		if (props.containsKey(key))
		{
			value = CastUtil.castBoolean(props.getProperty(key));
		}
		return value;
	}
	
	public static double getDouble(Properties props, String key, double defaultValue)
	{
		double value = defaultValue;
		if (props.containsKey(key))
		{
			value = CastUtil.castDouble(props.getProperty(key));
		}
		return value;
	}
	
	
	
	
	

}
