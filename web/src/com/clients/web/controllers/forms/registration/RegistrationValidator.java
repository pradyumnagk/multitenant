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

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import com.clients.web.facades.UserFacade;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class RegistrationValidator implements Validator
{

	protected UserFacade userFacade;

	@Override
	public boolean supports(final Class clazz)
	{
		return RegistrationInfo.class.equals(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors)
	{
		final RegistrationInfo info = (RegistrationInfo) target;
		rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
		rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
		rejectIfEmptyOrWhitespace(errors, "street", "empty");
		rejectIfEmptyOrWhitespace(errors, "streetnumber", "empty");
		rejectIfEmptyOrWhitespace(errors, "zip", "empty");
		rejectIfEmptyOrWhitespace(errors, "city", "empty");
		rejectIfEmptyOrWhitespace(errors, "country", "empty");

		if (StringUtils.isEmpty(info.getLogin()))
		{
			errors.rejectValue("login", "empty");
		}
		else if (userFacade.userExists(info.getLogin()))
		{
			errors.rejectValue("login", "register.login.exists");
		}

		if (StringUtils.isEmpty(info.getPassword()))
		{
			errors.rejectValue("password", "empty");
		}
		else if (StringUtils.isEmpty(info.getPassword2()))
		{
			errors.rejectValue("password2", "empty");
		}
		else if (!info.getPassword().equals(info.getPassword2()))
		{
			errors.rejectValue("password", "register.password.mismatch");
			errors.rejectValue("password2", "empty");
		}
		if (StringUtils.isEmpty(info.getMail()))
		{
			errors.rejectValue("mail", "empty");
		}
		else
		{
			final Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
			final Matcher matcher = pattern.matcher(info.getMail());
			if (!matcher.matches())
			{
				errors.rejectValue("mail", "register.invalid.email");
			}
		}

		if (StringUtils.isEmpty(info.getCountry()))
		{
			errors.rejectValue("country", "empty");
		}

	}

	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}
}
