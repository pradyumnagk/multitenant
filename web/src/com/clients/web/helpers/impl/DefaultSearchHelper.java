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
package com.clients.web.helpers.impl;

import de.hybris.platform.solrfacetsearch.search.FacetSearchException;
import de.hybris.platform.solrfacetsearch.search.FacetSearchService;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.SearchHelper;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class DefaultSearchHelper extends AbstractHelper implements SearchHelper
{
	protected final static Logger LOG = Logger.getLogger(DefaultSearchHelper.class.getName());

	protected CatalogHelper catalogHelper;
	protected FacetSearchService facetSearchService;

	public final static String SEARCH_QUERY = "searchQuery";
	public final static String SEARCH_RESULT = "searchResult";
	public final static String CURRENT_SEARCH_SERVICE = "currentSearchService";

	@Override
	public String convert(final SearchQuery search)
	{
		String ret = null;
		try
		{
			ret = facetSearchService.convertSearchQueryToString(search);
		}
		catch (final FacetSearchException e)
		{
			LOG.error("An error occured while convertion to string representation!", e);
		}
		return ret;
	}

	@Override
	public SearchQuery getCurrentSearchQuery(final HttpServletRequest request)
	{
		return (SearchQuery) request.getSession().getAttribute(SEARCH_QUERY);
	}

	@Override
	public SearchResult getCurrentSearchResult(final HttpServletRequest request)
	{
		return (SearchResult) request.getSession().getAttribute(SEARCH_RESULT);
	}

	@Override
	public void setCurrentSearchResult(final HttpServletRequest request, final SearchResult result)
	{
		request.getSession().setAttribute(SEARCH_RESULT, result);
	}

	@Override
	public void removeCurrentSearchResult(final HttpServletRequest request)
	{
		request.getSession().setAttribute(SEARCH_RESULT, null);
	}

	@Override
	public void setCurrentSearchQuery(final HttpServletRequest request, final SearchQuery query)
	{
		request.getSession().setAttribute(SEARCH_QUERY, query);

	}

	@Override
	public String getCurrentServiceName(final HttpServletRequest request)
	{
		return (String) request.getSession().getAttribute(CURRENT_SEARCH_SERVICE);
	}

	@Override
	public void setCurrentServiceName(final HttpServletRequest request, final String serviceName)
	{
		request.getSession().setAttribute(CURRENT_SEARCH_SERVICE, serviceName);
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}

	public void setFacetSearchService(final FacetSearchService facetSearchService)
	{
		this.facetSearchService = facetSearchService;
	}


}
