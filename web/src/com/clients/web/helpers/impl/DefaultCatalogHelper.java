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

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import com.clients.web.helpers.CatalogHelper;


public class DefaultCatalogHelper extends AbstractHelper implements CatalogHelper
{

	protected CMSSiteService cmsSiteService;
	protected CatalogService catalogService;

	@Override
	public String extractCatalogId(final String path)
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
	public void setCurrentCatalogVersionById(final String catalogId) throws CMSItemNotFoundException
	{
		final CatalogVersionModel catalogVersion = catalogService.getSessionCatalogVersion(catalogId);
		cmsSiteService.setCurrentCatalogVersion(catalogVersion);
	}

	@Override
	public CatalogModel getCatalogModel(final String itemId)
	{
		//catalogService.get
		//CatalogManager.getInstance().geCa
		return null;
	}

	@Override
	public CatalogVersionModel getCurrentCatalogVersion()
	{
		return cmsSiteService.getCurrentCatalogVersion();
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	@Override
	public void setCatalogService(final CatalogService catalogService)
	{
		this.catalogService = catalogService;
	}


}
