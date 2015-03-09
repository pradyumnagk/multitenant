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
package com.clients.web.controllers.forms.registration;


import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.servicelayer.i18n.I18NService;
import com.clients.web.facades.UserFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


public class RegistrationController extends SimpleFormController
{

	protected UserFacade userFacade;
	protected I18NService i18nService;

	@Override
	protected Map<String, Object> referenceData(final HttpServletRequest request, final Object command, final Errors errors)
			throws Exception
	{
		final Map<String, Object> data = new HashMap<String, Object>();
		final Set<CountryModel> countries = i18nService.getAllCountries();
		data.put("countries", countries);
		return data;
	}

	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request, final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception
	{
		final RegistrationInfo info = (RegistrationInfo) command;
		userFacade.registerUser(info);
		return super.onSubmit(request, response, command, errors);
	}

	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}

	public void setI18nService(final I18NService service)
	{
		i18nService = service;
	}


}
