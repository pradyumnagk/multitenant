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
package com.clients.web.controllers.pages.impl;

import com.clients.web.controllers.pages.ResourcePathProvider;
import com.clients.web.helpers.RequestHelper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Required;


public class DefaultResourcePathProvider implements ResourcePathProvider
{
	private RequestHelper requestHelper;

	@Override
	public String getResourcePath(final HttpServletRequest httpServletRequest)
	{
		return requestHelper.getResourcePath(httpServletRequest);
	}

	@Required
	public void setRequestHelper(final RequestHelper requestHelper)
	{
		this.requestHelper = requestHelper;
	}
}
