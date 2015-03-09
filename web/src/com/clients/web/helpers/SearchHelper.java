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
package com.clients.web.helpers;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;

import javax.servlet.http.HttpServletRequest;


public interface SearchHelper
{
	
	String convert(SearchQuery query);
	
	void setCurrentSearchResult(HttpServletRequest request, SearchResult result);

	SearchResult getCurrentSearchResult(HttpServletRequest request);

	void removeCurrentSearchResult(HttpServletRequest request);

	void setCurrentSearchQuery(HttpServletRequest request, SearchQuery query);

	SearchQuery getCurrentSearchQuery(HttpServletRequest request);

	String getCurrentServiceName(HttpServletRequest request);

	void setCurrentServiceName(HttpServletRequest request, String serviceName);




}
