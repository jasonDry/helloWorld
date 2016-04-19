package com.dry.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 字符串辅助类
 * @author jason
 * @date 2016年4月19日 下午11:06:31
 * 
 */
public class StringUtil
{
	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str)
	{
		if (null != null)
		{
			str = str.trim();
		}
		return StringUtils.isEmpty(str);

	}
}
