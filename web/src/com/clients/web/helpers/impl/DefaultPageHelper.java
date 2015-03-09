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

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.impl.DefaultCMSPageService;
import com.clients.web.helpers.PageHelper;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class DefaultPageHelper extends AbstractHelper implements PageHelper
{
	private final static Logger LOG = Logger.getLogger(DefaultCMSPageService.class.getName());
	public static final String PAGE_KEY = "currentPage";
	private CMSPageService cmsPageService;

	@Override
	public String extractPageLabelOrId(final String path)
	{
		final String[] components = getPathComponents(path);
		if (components != null && components.length > 0)
		{
			return components[0];
		}
		else
		{
			return null;
		}
	}

	@Override
	public AbstractPageModel getCurrentPage(final HttpServletRequest request)
	{
		return (AbstractPageModel) request.getAttribute(PAGE_KEY);
	}

	@Override
	public void setCurrentPage(final HttpServletRequest request, final AbstractPageModel page)
	{
		request.setAttribute(PAGE_KEY, page);
	}

	@Override
	public boolean isCurrentPageContentPage(final HttpServletRequest request)
	{
		final AbstractPageModel page = getCurrentPage(request);
		return page instanceof ContentPageModel;
	}

	@Override
	public AbstractPageModel getPageByLabelOrIdSafe(final String labelOrId)
	{
		AbstractPageModel abstractPageModel = null;
		try
		{
			abstractPageModel = cmsPageService.getPageByLabelOrId(labelOrId);
		}
		catch (final CMSItemNotFoundException e)
		{
			LOG.debug("Cannot retrive page by label or id ( " + labelOrId + " )", e);
		}
		return abstractPageModel;
	}

	public void setCmsPageService(final CMSPageService cmsPageService)
	{
		this.cmsPageService = cmsPageService;
	}


}
