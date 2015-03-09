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
package com.clients.web.servlets;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.GenericFilterBean;


/**
 * Filter base class that guarantees to be just executed once per request for logged user, on any servlet container. It
 * provides a {@link #doFilterInternal} method with HttpServletRequest and HttpServletResponse arguments.
 * 
 * <p>
 * The {@link #getAlreadyFilteredAttributeName(HttpServletRequest)} method determines how to identify that a request is
 * already filtered. The default implementation is based on the configured name of the concrete filter instance.
 * <p>
 * The {@link #getLogedUserId(HttpServletRequest)} method returns identifier for currently logged in user.
 * 
 * 
 * @author karol.walczak
 */

public abstract class AbstractOncePerUserRequestFilter extends GenericFilterBean
{

	public static final String ALREADY_FILTERED_SUFFIX = ".FILTERED";
	public static final String DOT_SUFFIX = ".";

	@Override
	public final void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws ServletException, IOException
	{

		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse))
		{
			throw new ServletException("LoginAwareRequesFilter just supports HTTP requests");
		}
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName(httpRequest);
		if (request.getAttribute(alreadyFilteredAttributeName) != null || shouldNotFilter(httpRequest))
		{
			// Proceed without invoking this filter...
			filterChain.doFilter(request, response);
		}
		else
		{
			// Do invoke this filter...
			request.setAttribute(alreadyFilteredAttributeName, Boolean.TRUE);
			doFilterInternal(httpRequest, httpResponse, filterChain);
		}
	}

	protected abstract String getLogedUserId(HttpServletRequest request);


	@SuppressWarnings("unused")
	protected String getAlreadyFilteredAttributeName(final HttpServletRequest request)
	{
		String name = getFilterName();
		if (name == null)
		{
			name = getClass().getName();
		}
		return name + ALREADY_FILTERED_SUFFIX
				+ (StringUtils.isNotBlank(getLogedUserId(request)) ? (DOT_SUFFIX + getLogedUserId(request)) : "");
	}

	@SuppressWarnings("unused")
	protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException
	{
		return false;
	}

	protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException;
}
