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

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import com.clients.web.controllers.pages.PageSourceObjectProvider;
import com.clients.web.helpers.PageHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


public class ContentPageSourceObjectProvider implements PageSourceObjectProvider<AbstractPageModel>
{
	private CMSSiteService cmsSiteService;
	private CMSPageService cmsPageService;
	private PageHelper pageHelper;

	@Override
	public AbstractPageModel getPageSourceObject(final String resourcePath, final HttpServletRequest request,
			final HttpServletResponse response) throws CMSItemNotFoundException
	{
		String labelOrId = pageHelper.extractPageLabelOrId(resourcePath);
		if (StringUtils.isEmpty(labelOrId))
		{
			final ContentPageModel homePage = cmsPageService.getHomepage();
			if (homePage == null)
			{
				final CMSSiteModel site = cmsSiteService.getCurrentSite();
				labelOrId = site.getStartPageLabel();
			}
			else
			{
				labelOrId = homePage.getLabelOrId();
			}
		}
		return cmsPageService.getPageByLabelOrId(labelOrId);
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	public void setCmsPageService(final CMSPageService cmsPageService)
	{
		this.cmsPageService = cmsPageService;
	}

	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}
}
