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
package com.clients.web.controllers.components;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.URLHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class LanguagesController implements Controller
{
	protected final static Logger LOG = Logger.getLogger(LanguagesController.class.getName());

	protected String view;
	protected CatalogHelper catalogHelper;
	protected URLHelper urlHelper;

	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();

		final CatalogVersionModel catalogVersion = catalogHelper.getCurrentCatalogVersion();
		if (catalogVersion != null)
		{
			final Collection<LanguageModel> languagesCol = catalogVersion.getLanguages();
			model.put("languages", languagesCol);
		}
		return new ModelAndView(getView(), model);
	}

	public String getView()
	{
		return view;
	}

	public void setView(final String view)
	{
		this.view = view;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}

	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

}
