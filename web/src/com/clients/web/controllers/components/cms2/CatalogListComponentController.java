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
package com.clients.web.controllers.components.cms2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;


public class CatalogListComponentController extends AbstractCMSComponentController<CatalogListComponentModel1>
{

	protected CMSSiteService cmsSiteService;

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final CatalogListComponentModel1 component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		List<CatalogModel> catalogs = null;
		if (component.isUseCatalogsOfStore())
		{
			catalogs = cmsSiteService.getCurrentSite().getProductCatalogs();
		}
		else
		{
			catalogs = component.getCatalogs();
		}
		model.put("catalogs", catalogs);
		return new ModelAndView(getView(), model);
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

}
