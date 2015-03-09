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
package com.clients.web.interceptors;

import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;
import com.clients.web.facades.SearchFacade;

import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class SolrFacetSearchInterceptor extends AbstractInterceptor
{

	private final static Logger LOG = Logger.getLogger(SolrFacetSearchInterceptor.class);

	public static final String LEGAL_SOLR_SEARCH = "legalSolrSearch";

	private final String SOLR_QUERY = "query";
	private SearchFacade searchFacade;

	//sorted escape characters for lucene(solr)
	private static final char[] sortedEscapeLuceneChars =
	{ '!', '"', '&', '(', ')', '*', '+', '-', ':', '?', '[', '\\', ']', '^', '{', '|', '}', '~' };

	@Override
	public boolean doPreHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final String actionName) throws ServletException
	{
		String searchTxt = request.getParameter(SOLR_QUERY);
		final SearchQuery query = searchHelper.getCurrentSearchQuery(request);
		request.getSession().setAttribute(LEGAL_SOLR_SEARCH, Boolean.TRUE);
		if (query == null)
		{
			LOG.error("No solr query found. Please check your configuration.");
		}
		else if (searchTxt != null)
		{
			searchTxt = searchTxt.trim();
			if (searchTxt.length() > 0)
			{
				final String[] txts = searchTxt.split(" ");
				final boolean legalInput = this.checkSearchInputs(txts);
				if (legalInput)
				{
					query.clearAllFields();
					for (final String s : txts)
					{
						query.search(s, SearchQuery.Operator.OR);
					}
					final SearchResult result = this.searchFacade.search(query);
					searchHelper.setCurrentSearchResult(request, result);
				}
				else
				{
					LOG.error("Illegal facet search keyword [" + searchTxt + "]");
					request.getSession().setAttribute(LEGAL_SOLR_SEARCH, Boolean.FALSE);
				}
			}
			else
			{
				request.getSession().setAttribute(LEGAL_SOLR_SEARCH, Boolean.FALSE);
			}
		}
		return true;
	}

	public void setSearchFacade(final SearchFacade searchFacade)
	{
		this.searchFacade = searchFacade;
	}

	private boolean checkSearchInputs(final String[] inputs)
	{
		boolean legalInput = true;
		for (final String input : inputs)
		{
			final char firstChar = input.toLowerCase().charAt(0);
			if (Arrays.binarySearch(sortedEscapeLuceneChars, firstChar) >= 0)
			{
				legalInput = false;
				break;
			}
		}
		return legalInput;
	}

}
