/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2012 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.clients.web.data;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;


public class PaginationData implements Serializable
{

	private String id;
	private int pageNumber;
	private int pageSize;
	private String orderKey;

	public PaginationData()
	{
		this.id = "";
		this.pageNumber = 1;
		this.pageSize = Integer.MAX_VALUE;
		this.orderKey = "";
	}

	public boolean isOrderKeyAvailable()
	{
		return !StringUtils.isEmpty(orderKey);
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(final int pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(final int pageSize)
	{
		this.pageSize = pageSize;
	}

	public String getOrderKey()
	{
		return orderKey;
	}

	public void setOrderKey(final String orderKey)
	{
		this.orderKey = orderKey;
	}

	public void setId(final String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

}
