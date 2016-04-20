package com.dry.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * @Description: 集合辅助类
 * @author jason
 * @date 2016年4月20日 下午10:43:37
 * 
 */
public class CollectionUtil
{
	public static boolean isEmpty(Collection<?> coll)
	{
		return CollectionUtils.isEmpty(coll);
	}

	public static boolean isNotEmpty(Collection<?> coll)
	{
		return !isEmpty(coll);
	}

	public static boolean isEmpty(Map<?, ?> map)
	{
		return MapUtils.isEmpty(map);
	}

	public static boolean isNotEmpty(Map<?, ?> map)
	{
		return !MapUtils.isEmpty(map);
	}

}
