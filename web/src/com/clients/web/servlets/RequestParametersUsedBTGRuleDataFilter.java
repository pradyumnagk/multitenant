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

import de.hybris.platform.btg.events.RequestParametersUsedBTGRuleDataEvent;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This filter handles "Request parameters are used in request" use case. Request parameters are extracted from the
 * request and used to construct a {@link RequestParametersUsedBTGRuleDataEvent}. This event is then published using
 * {@link #publishRequestScopedBTGRuleDataInsertingEvent(de.hybris.platform.btg.events.AbstractBTGRuleDataEvent, HttpServletRequest)}
 * which ensures that the collected request data will be automatically removed at the end of the request processing.
 */
public class RequestParametersUsedBTGRuleDataFilter extends AbstractBTGRuleDataFilter<Map<String, String[]>>
{


	@Override
	protected Map<String, String[]> getBusinessObject(final HttpServletRequest httpServletRequest,
			final HttpServletResponse response) throws ServletException, IOException
	{
		return httpServletRequest.getParameterMap();
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{
		final Map<String, String[]> paramMap = getBusinessObject(httpServletRequest, response);

		if (!paramMap.isEmpty())
		{
			final RequestParametersUsedBTGRuleDataEvent dataInsertingEvent = new RequestParametersUsedBTGRuleDataEvent(paramMap);
			this.publishRequestScopedBTGRuleDataInsertingEvent(dataInsertingEvent, httpServletRequest);
		}
		filterChain.doFilter(httpServletRequest, response);
	}
}
