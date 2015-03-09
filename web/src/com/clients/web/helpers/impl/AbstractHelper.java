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

import static org.apache.commons.lang.StringUtils.split;
import static org.apache.commons.lang.StringUtils.strip;
import static org.apache.commons.lang.StringUtils.stripToEmpty;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.model.CatalogVersionModel;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.UrlPathHelper;


public abstract class AbstractHelper
{
	protected UrlPathHelper urlPathHelper = new UrlPathHelper();

	protected CatalogService catalogService;

	protected String[] getPathComponents(final String path)
	{
		final String result = stripToEmpty(strip(path, "/"));
		return split(result, "/");
	}

	protected String getPath(final String requestUri, final HttpServletRequest request)
	{
		String result = requestUri;
		final String contextPath = urlPathHelper.getContextPath(request);
		if (requestUri.startsWith(contextPath))
		{
			result = requestUri.substring(contextPath.length());
		}
		return result;
	}

	public void setUrlPathHelper(final UrlPathHelper urlPathHelper)
	{
		this.urlPathHelper = urlPathHelper;
	}

	public void setCatalogService(final CatalogService catalogService)
	{
		this.catalogService = catalogService;
	}

	protected CatalogVersionModel getCatalogVersion(final String catalogId)
	{
		return catalogService.getSessionCatalogVersion(catalogId);
	}
}
