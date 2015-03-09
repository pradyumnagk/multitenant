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
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class SearchData implements Serializable
{

	public class FacetAction
	{
		private final String facet;
		private final String value;
		private final boolean add;

		public FacetAction(final String facet, final String value, final String method)
		{
			this.facet = facet;
			this.value = value;
			this.add = "add".equals(StringUtils.lowerCase(method));
		}

		public String getFacet()
		{
			return facet;
		}

		public String getValue()
		{
			return value;
		}

		public boolean isAdd()
		{
			return add;
		}

	}

	private boolean clearAllFields;
	boolean searchReferer;
	boolean searchPage;
	private String actionName;
	private List<String> path;
	private FacetAction facetAction;

	public SearchData()
	{
		this.path = new ArrayList<String>();
	}

	public boolean isClearAllFields()
	{
		return clearAllFields;
	}

	public void setClearAllFields(final boolean clearAllFields)
	{
		this.clearAllFields = clearAllFields;
	}

	public String getActionName()
	{
		return actionName;
	}

	public void setActionName(final String actionName)
	{
		this.actionName = actionName;
	}

	public boolean isSearchReferer()
	{
		return searchReferer;
	}

	public void setSearchReferer(final boolean searchReferer)
	{
		this.searchReferer = searchReferer;
	}

	public boolean isSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(final boolean searchPage)
	{
		this.searchPage = searchPage;
	}

	public List<String> getPath()
	{
		return path;
	}

	public void setPath(final List<String> path)
	{
		this.path = path;
	}

	public void addFacetAction(final String facet, final String value, final String method)
	{
		this.facetAction = new FacetAction(facet, value, method);
	}

	public boolean hasFacetAction()
	{
		return this.facetAction != null;
	}

	public FacetAction getFacetAction()
	{
		return this.facetAction;
	}


}
