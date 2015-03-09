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
package com.clients.web.helpers.impl;

import com.clients.web.helpers.RequestHelper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UrlPathHelper;


public class DefaultRequestHelper extends AbstractHelper implements RequestHelper
{
	//final static private Logger LOG = Logger.getLogger(DefaultRequestHelper.class.getName());

	protected UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Override
	public String getResourcePath(final HttpServletRequest request)
	{
		final String path = getPath(urlPathHelper.getRequestUri(request), request);
		final String[] pathComponents = getPathComponents(path);
		if (pathComponents.length <= 2)
		{
			return null;
		}
		return "/" + StringUtils.join(ArrayUtils.subarray(pathComponents, 2, pathComponents.length), '/');
	}

	@Override
	public String getActionName(final HttpServletRequest request)
	{
		final String path = getPath(urlPathHelper.getRequestUri(request), request);
		final String[] pathComponents = getPathComponents(path);
		if (pathComponents.length < 2)
		{
			return null;
		}
		return pathComponents[1];
	}

}
