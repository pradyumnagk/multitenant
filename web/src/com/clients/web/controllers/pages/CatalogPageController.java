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

import de.hybris.platform.cms2.model.pages.AbstractPageModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CatalogPageController extends AbstractPageController
{
	@Override
	protected AbstractPageModel getPage(final String resourcePath, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{
		final String catalogId = catalogHelper.extractCatalogId(resourcePath);
		catalogHelper.setCurrentCatalogVersionById(catalogId);
		return cmsPageService.getPageByCatalogId(catalogId);
	}

}
