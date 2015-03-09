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

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import com.clients.web.facades.UserFacade;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class LoginValidator implements Validator
{

	protected UserFacade userFacade;

	@Override
	public void validate(final Object target, final Errors errors)
	{
		final LoginInfo loginInfo = (LoginInfo) target;
		rejectIfEmptyOrWhitespace(errors, "login", "login.login.empty");
		rejectIfEmptyOrWhitespace(errors, "password", "login.password.empty");
		if (!errors.hasErrors() && !userFacade.checkCredentials(loginInfo.getLogin(), loginInfo.getPassword()))
		{
			errors.reject("credentials", "login.invalid");
		}
	}

	@Override
	public boolean supports(final Class clazz)
	{
		return LoginInfo.class.equals(clazz);
	}

	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}

}
