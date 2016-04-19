package com.dry.util;

/**
 * @Description: 数据类型转换辅助类
 * @author jason
 * @date 2016年4月19日 下午11:02:21
 * 
 */
public final class CastUtil
{
	public static int castInt(Object obj)
	{
		return castInt(obj, 0);
	}

	public static int castInt(Object obj, int defaultValue)
	{
		int value = defaultValue;
		if (obj != null)
		{
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue))
			{
				//TODO 是否需要添加异常处理，异常后使用默认值
				value = Integer.parseInt(strValue);
			}
		}
		return value;
	}

	public static String castString(Object obj, String defaultValue)
	{
		return obj != null ? String.valueOf(obj) : defaultValue;
	}

	public static String castString(Object obj)
	{
		return castString(obj, "");
	}
	
	public static long castLong(Object obj, long defaultValue)
	{
		long value = defaultValue;
		if (obj != null)
		{
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue))
			{
				//TODO 是否需要添加异常处理，异常后使用默认值
				value = Long.parseLong(strValue);
			}
		}
		return value;
	}
	
	public static long castLong(Object obj)
	{
		return castLong(obj,0L);
	}
	
	public static double castDouble(Object obj, double defaultValue)
	{
		double value = defaultValue;
		if (obj != null)
		{
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue))
			{
				//TODO 是否需要添加异常处理，异常后使用默认值
				value = Double.parseDouble(strValue);
			}
		}
		return value;
	}
	
	public static double castDouble(Object obj)
	{
		return castDouble(obj,0D);
	}
	
	public static boolean castBoolean(Object obj, boolean defaultValue)
	{
		boolean value = defaultValue;
		if (obj != null)
		{
			String strValue = castString(obj);
			if (StringUtil.isNotEmpty(strValue))
			{
				//TODO 是否需要添加异常处理，异常后使用默认值
				value = Boolean.parseBoolean(strValue);
			}
		}
		return value;
	}
	
	public static boolean castBoolean(Object obj)
	{
		return castBoolean(obj,false);
	}
}
