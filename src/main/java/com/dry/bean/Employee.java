package com.dry.bean;

import java.io.Serializable;
import java.util.Date;

/** 
* @Description: 员工实体类
* @author jason 
* @date 2016年4月16日 下午10:25:50 
*  
*/
public class Employee implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String postion;
	private String gender;
	/**
	 * 入职时间
	 */
	private Date entryTime;
	/**
	 * 
	 */
	public Employee()
	{
	}
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the postion
	 */
	public String getPostion()
	{
		return postion;
	}
	/**
	 * @param postion the postion to set
	 */
	public void setPostion(String postion)
	{
		this.postion = postion;
	}
	/**
	 * @return the gender
	 */
	public String getGender()
	{
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	/**
	 * @return the entryTime
	 */
	public Date getEntryTime()
	{
		return entryTime;
	}
	/**
	 * @param entryTime the entryTime to set
	 */
	public void setEntryTime(Date entryTime)
	{
		this.entryTime = entryTime;
	}
	
	@Override
	public String toString()
	{
		return "Employee [id=" + id + ", name=" + name + ", postion=" + postion + ", gender=" + gender + ", entryTime="
				+ entryTime + "]";
	}
	
}
