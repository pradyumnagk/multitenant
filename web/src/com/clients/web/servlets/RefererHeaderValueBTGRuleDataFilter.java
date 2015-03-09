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

import de.hybris.platform.btg.events.RefererHeaderUsedBTGRuleDataEvent;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This filter handles "HTTP header 'referer' is used in request" use case. The header is extracted from the request and
 * used to construct a {@link RefererHeaderUsedBTGRuleDataEvent}. This event is then published using
 * {@link #publishRequestScopedBTGRuleDataInsertingEvent(de.hybris.platform.btg.events.AbstractBTGRuleDataEvent, HttpServletRequest)}
 * which ensures that the collected request data will be automatically removed at the end of the request processing.
 */
public class RefererHeaderValueBTGRuleDataFilter extends AbstractBTGRuleDataFilter<String>
{
	private static final String REFERER_HEADER_NAME = "Referer";

	@Override
	protected String getBusinessObject(final HttpServletRequest httpServletRequest, final HttpServletResponse response)
			throws ServletException, IOException
	{
		return httpServletRequest.getHeader(REFERER_HEADER_NAME);
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{
		final String refererValue = getBusinessObject(httpServletRequest, response);
		if (refererValue != null)
		{
			//			final String requestURI = httpServletRequest.getRequestURI();
			//			final String requestURL = httpServletRequest.getRequestURL().toString();
			//			System.out.println("requestURI: " + requestURI);
			//			System.out.println("requestURL: " + requestURL);
			//			System.out.println("refererValue: " + refererValue);
			final RefererHeaderUsedBTGRuleDataEvent dataInsertingEvent = new RefererHeaderUsedBTGRuleDataEvent(refererValue);
			this.publishRequestScopedBTGRuleDataInsertingEvent(dataInsertingEvent, httpServletRequest);
		}
		filterChain.doFilter(httpServletRequest, response);
	}
}
