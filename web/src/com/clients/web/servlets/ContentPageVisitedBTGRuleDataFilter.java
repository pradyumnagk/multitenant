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

import de.hybris.platform.btg.events.ContentPageVisitedBTGRuleDataEvent;
import de.hybris.platform.cms2.model.pages.ContentPageModel;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This filter handles "User has visited a Content Page" use case. Based on the http request it determines a Content
 * Page that has been requested by the web user, gets the Content Page PK and publishes a
 * {@link ContentPageVisitedBTGRuleDataEvent} that encapsulates the PK.
 */
public class ContentPageVisitedBTGRuleDataFilter extends AbstractBTGRuleDataFilter<ContentPageModel>
{

	@Override
	protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{
		final ContentPageModel cpm = getBusinessObject(httpServletRequest, response);
		if (cpm != null)
		{
			final String contentPagePK = cpm.getPk().toString();
			final ContentPageVisitedBTGRuleDataEvent dataInsertingEvent = new ContentPageVisitedBTGRuleDataEvent(contentPagePK);
			this.publishBTGRuleDataEvent(dataInsertingEvent, httpServletRequest);
		}

		filterChain.doFilter(httpServletRequest, response);
	}
}
