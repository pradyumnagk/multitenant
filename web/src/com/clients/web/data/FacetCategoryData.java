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


public class FacetCategoryData implements Serializable
{

	private String name;
	private Collection<FacetValueData> facets;
	private boolean selected;

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(final boolean selected)
	{
		this.selected = selected;
	}

	public FacetCategoryData()
	{
		this.facets = new ArrayList<FacetValueData>();
	}

	public boolean isFacetsAvailable()
	{
		return this.facets != null && !this.facets.isEmpty();
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public Collection<FacetValueData> getFacets()
	{
		return facets;
	}

	public void setFacets(final Collection<FacetValueData> facets)
	{
		this.facets = facets;
	}

}
