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


public class FacetValueData implements Serializable, Comparable<FacetValueData>
{

	private final String code;
	private final String name;
	private final long count;
	private final boolean selected;

	public FacetValueData(final String code, final String name, final long count, final boolean selected)
	{
		this.code = code;
		this.name = name;
		this.count = count;
		this.selected = selected;
	}

	public FacetValueData(final String name, final long count, final boolean selected)
	{
		this(name, name, count, selected);
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public long getCount()
	{
		return count;
	}

	public boolean isSelected()
	{
		return selected;
	}

	@Override
	public int compareTo(final FacetValueData o)
	{
		return this.getName().compareToIgnoreCase(o.getName());
	}

}
