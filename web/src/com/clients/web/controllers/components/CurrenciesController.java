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

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.servicelayer.i18n.I18NService;
import com.clients.web.helpers.URLHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class CurrenciesController implements Controller
{
	protected final static Logger LOG = Logger.getLogger(CurrenciesController.class.getName());

	protected String view;
	protected I18NService i18nService;
	protected URLHelper urlHelper;

	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();

		final Set<CurrencyModel> currencies = i18nService.getAllCurrencies();
		if (currencies != null)
		{
			model.put("currencies", currencies);
		}
		else
		{
			model.put("currencies", Collections.emptySet());
		}

		model.put("currentURL", urlHelper.getCurrentURL(request));
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

	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

	public void setI18nService(final I18NService service)
	{
		i18nService = service;
	}

}
