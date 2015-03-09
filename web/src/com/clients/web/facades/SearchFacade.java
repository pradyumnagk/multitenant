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
package com.clients.web.facades;

import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;
import com.clients.web.data.FacetCategoryData;
import com.clients.web.data.PaginationData;
import com.clients.web.data.ProductResultData;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



public interface SearchFacade
{
	public final static String FACET = "f";
	public final static String VALUE = "v";
	public final static String METHOD = "m";
	public final static String CLEAR_ALL_FIELDS = "clearAll";

	List<FacetCategoryData> getFacetCategories(HttpServletRequest request, PaginationData paginationData);

	SearchResult search(SearchQuery query);

	ProductResultData getProducts(HttpServletRequest request, PaginationData paginationData);

	ProductResultData getProductsWithSimpleSearch(String queryString, PaginationData paginationData);

	ProductResultData getProductsWithSimpleSearch(HttpServletRequest request, String queryString, PaginationData paginationData);

	String getQueryString(HttpServletRequest request);

	public String getSearchPageLabel();

}
