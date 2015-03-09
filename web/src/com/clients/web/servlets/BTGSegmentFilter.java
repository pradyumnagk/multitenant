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

import de.hybris.platform.btg.enums.BTGConditionEvaluationScope;
import de.hybris.platform.btg.enums.BTGEvaluationMethod;
import de.hybris.platform.btg.enums.BTGResultScope;
import de.hybris.platform.btg.segment.SegmentEvaluationException;
import de.hybris.platform.btg.servicelayer.services.evaluator.impl.SessionBTGEvaluationContextProvider;
import de.hybris.platform.btg.services.BTGEvaluationService;
import de.hybris.platform.btg.services.BTGResultService;
import de.hybris.platform.btg.services.impl.BTGEvaluationContext;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import com.clients.web.servlets.util.FilterSpringUtil;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class BTGSegmentFilter extends CMSSiteFilter
{
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger(BTGSegmentFilter.class);

	@Override
	protected void doFilterInternal(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse,
			final FilterChain filterChain) throws ServletException, IOException
	{
		if (!Boolean.parseBoolean(httpRequest.getParameter("proceedLogout")))
		{
			final BTGEvaluationService btgEvaluationService = getBTGEvaluationService(httpRequest);
			final BTGResultService btgResultService = getBTGResultService(httpRequest);
			final UserService userService = getUserService(httpRequest);
			final UserModel currentUser = userService.getCurrentUser();
			final CMSSiteService cmsSiteService = getCMSSiteService(httpRequest);
			final CMSSiteModel currentSite = cmsSiteService.getCurrentSite();
			if (currentSite == null)
			{
				LOG.error("current CMS site is not set! BTG evaluation cannot be done. Please update CMS store configuration!");
			}
			else
			{
				try
				{
					BTGEvaluationContext context = null;
					if (isPreviewDataModelValid(httpRequest))
					{
						//preview for BTGCockpit
						//always invoke FULL evaluation method and store results per session   
						context = new BTGEvaluationContext(BTGConditionEvaluationScope.ONLINE, BTGEvaluationMethod.FULL,
								BTGResultScope.SESSION);
					}
					else
					{
						//process normal request (i.e. normal browser non-btgcockpit request)
						//the evaluation method will be taken from segment!
						context = new BTGEvaluationContext(BTGConditionEvaluationScope.ONLINE, null);
					}
					//right now we basically invalidate all results, because we don't specify  BTGRuleType 
					//i.e. when user would like to invalidate only some type of rules he should specify this parameter
					btgResultService.invalidateEvaluationResults(currentSite, currentUser, context, null);
					btgEvaluationService.evaluateAllSegments(currentUser, currentSite, context);

					getSessionService(httpRequest).setAttribute(SessionBTGEvaluationContextProvider.BTG_CURRENT_EVALUATION_CONTEXT,
							context);
				}
				catch (final SegmentEvaluationException e)
				{
					throw new ServletException(e.getMessage(), e);
				}
			}
		}

		try
		{
			filterChain.doFilter(httpRequest, httpResponse);
		}
		finally
		{
			//Clear all request-scoped data for this request.
			RequestScopedBTGRuleDataServletRequestListener.publishDataRemovingEvents(httpRequest);
		}
	}

	protected BTGEvaluationService getBTGEvaluationService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "btgEvaluationService", BTGEvaluationService.class);
	}

	protected BTGResultService getBTGResultService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "btgResultService", BTGResultService.class);
	}
}
