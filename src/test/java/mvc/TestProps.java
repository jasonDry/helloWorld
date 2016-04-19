package mvc;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.dry.util.PropsUtil;

/** 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author jason 
* @date 2016年4月19日 下午11:41:25 
*  
*/
public class TestProps
{
	@Test
	public void printProps()
	{
		Properties props = PropsUtil.loadProps("config.properties");
		Assert.assertEquals("demo",props.get("jdbc.username"));
		
	}
}
