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

import com.clients.web.helpers.RequestHelper;
import com.clients.web.helpers.SearchHelper;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public abstract class AbstractInterceptor extends HandlerInterceptorAdapter
{

	protected final static Logger LOG = Logger.getLogger(AbstractInterceptor.class.getName());

	protected RequestHelper requestHelper;
	protected SearchHelper searchHelper;
	protected List<String> actionNames;


	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws ServletException
	{
		final String action = requestHelper.getActionName(request);
		if (action != null && actionNames.contains(action))
		{
			return doPreHandle(request, response, handler, action);
		}
		return true;
	}

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final Exception ex) throws Exception
	{
		final String action = requestHelper.getActionName(request);
		if (action != null && actionNames.contains(action))
		{
			searchHelper.removeCurrentSearchResult(request);
		}
		super.afterCompletion(request, response, handler, ex);
	}

	public abstract boolean doPreHandle(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, String actionName) throws ServletException;

	public void setActionNames(final List<String> actionNames)
	{
		this.actionNames = actionNames;
	}

	public void setRequestHelper(final RequestHelper requestHelper)
	{
		this.requestHelper = requestHelper;
	}

	public void setSearchHelper(final SearchHelper searchHelper)
	{
		this.searchHelper = searchHelper;
	}

}
