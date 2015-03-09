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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class WishlistData implements Serializable
{

	private Date creationTime;
	private boolean _default;
	private String description;
	private Collection<WishlistEntryData> entries;

	public WishlistData()
	{
		this.entries = new ArrayList<WishlistEntryData>();
	}

	public Date getCreationTime()
	{
		return creationTime;
	}

	public void setCreationTime(final Date creationTime)
	{
		this.creationTime = creationTime;
	}

	public boolean isDefault()
	{
		return _default;
	}

	public void setDefault(final boolean _default)
	{
		this._default = _default;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public Collection<WishlistEntryData> getEntries()
	{
		return entries;
	}

	public void setEntries(final Collection<WishlistEntryData> entries)
	{
		this.entries = entries;
	}



}
