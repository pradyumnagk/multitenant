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
package com.clients.web.controllers.pages;

import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ContentPageController extends AbstractPageController
{
	private PageSourceObjectProvider<AbstractPageModel> pageSourceObjectProvider;

	@Override
	protected AbstractPageModel getPage(final String resourcePath, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{

		final AbstractPageModel result = pageSourceObjectProvider.getPageSourceObject(resourcePath, request, response);

		//check for a current catalog version
		final CatalogVersionModel version = catalogHelper.getCurrentCatalogVersion();
		if (version == null)
		{
			final CatalogModel defaultCatalog = cmsSiteService.getCurrentSite().getDefaultCatalog();
			if (defaultCatalog != null)
			{
				catalogHelper.setCurrentCatalogVersionById(defaultCatalog.getId());
			}
		}

		return result;
	}

	public void setPageSourceObjectProvider(final PageSourceObjectProvider<AbstractPageModel> pageSourceObjectProvider)
	{
		this.pageSourceObjectProvider = pageSourceObjectProvider;
	}
}
