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
package com.clients.web.controllers.forms.login;

import com.clients.web.data.UserData;
import com.clients.web.facades.UserFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


public class LoginController extends SimpleFormController
{

	protected static final String LOGGED_IN = "loggedIn";

	protected UserFacade userFacade;

	@Override
	protected boolean isFormSubmission(final HttpServletRequest request)
	{
		return "POST".equals(request.getMethod()) && request.getParameter(this.getClass().getSimpleName()) != null;
	}

	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request, final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception
	{
		final LoginInfo loginInfo = (LoginInfo) command;
		final String login = loginInfo.getLogin();
		final String password = loginInfo.getPassword();
		UserData user = null;
		if (userFacade.checkCredentials(login, password))
		{
			user = userFacade.getUser(login);
			userFacade.login(user);
		}
		request.setAttribute(LOGGED_IN, Boolean.valueOf(userFacade.isLoggedIn()));
		return new ModelAndView(getFormView(), errors.getModel());
	}

	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		request.setAttribute(LOGGED_IN, Boolean.valueOf(userFacade.isLoggedIn()));
		return super.handleRequest(request, response);
	}

	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}


}
