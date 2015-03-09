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
package com.clients.web.facades.impl;

import de.hybris.platform.catalog.CatalogService;
import com.clients.web.converters.CatalogConverter;
import com.clients.web.data.CatalogData;
import com.clients.web.facades.CatalogFacade;

import org.apache.log4j.Logger;


public class DefaultCatalogFacade implements CatalogFacade
{

	protected final static Logger LOG = Logger.getLogger(DefaultCatalogFacade.class.getName());

	protected CatalogService catalogService;
	protected CatalogConverter catalogConverter;


	public CatalogData getCatalog(final String catalogId)
	{
		return catalogConverter.convert(catalogService.getCatalog(catalogId));
	}

	public void setCatalogService(final CatalogService catalogService)
	{
		this.catalogService = catalogService;
	}

	public void setCatalogConverter(final CatalogConverter catalogConverter)
	{
		this.catalogConverter = catalogConverter;
	}
}
