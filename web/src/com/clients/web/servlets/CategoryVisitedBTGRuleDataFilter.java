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

import de.hybris.platform.btg.events.CategoryVisitedBTGRuleDataEvent;
import de.hybris.platform.category.model.CategoryModel;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This filter handles "User has visited a Category" use case. Based on the http request it determines a Category that
 * has been requested by the web user, gets the Category PK and publishes a {@link CategoryVisitedBTGRuleDataEvent} that
 * encapsulates the PK.
 */
public class CategoryVisitedBTGRuleDataFilter extends AbstractBTGRuleDataFilter<CategoryModel>
{

	@Override
	protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{

		final CategoryModel categoryModel = getBusinessObject(httpServletRequest, response);
		if (categoryModel != null)
		{
			final String categoryPK = categoryModel.getPk().toString();
			final CategoryVisitedBTGRuleDataEvent dataInsertingEvent = new CategoryVisitedBTGRuleDataEvent(categoryPK);
			this.publishBTGRuleDataEvent(dataInsertingEvent, httpServletRequest);
		}

		filterChain.doFilter(httpServletRequest, response);
	}
}
