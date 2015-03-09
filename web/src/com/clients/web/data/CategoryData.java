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
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class CategoryData implements Serializable
{
	private String catalogId;
	private String code;
	private String name;
	private List<String> path;

	public String getCode()
	{
		return code;
	}

	public void setCode(final String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getCatalogId()
	{
		return catalogId;
	}

	public void setCatalogId(final String catalogId)
	{
		this.catalogId = catalogId;
	}

	public String getPathAsString()
	{
		if (path != null)
		{
			return StringUtils.join(path.iterator(), '/');
		}
		else
		{
			return null;
		}
	}

	public List<String> getPath()
	{
		return path;
	}

	public void setPath(final List<String> path)
	{
		this.path = path;
	}

}
