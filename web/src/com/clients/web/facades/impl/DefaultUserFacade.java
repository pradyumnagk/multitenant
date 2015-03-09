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

import de.hybris.platform.btg.servicelayer.services.evaluator.impl.SessionBTGEvaluationContextProvider;
import de.hybris.platform.btg.services.BTGResultService;
import de.hybris.platform.btg.services.impl.BTGEvaluationContext;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.security.auth.AuthenticationService;
import de.hybris.platform.servicelayer.security.auth.InvalidCredentialsException;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import com.clients.web.controllers.forms.registration.RegistrationInfo;
import com.clients.web.converters.UserConverter;
import com.clients.web.data.UserData;
import com.clients.web.facades.UserFacade;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class DefaultUserFacade implements UserFacade
{
	private final static Logger LOG = Logger.getLogger(DefaultUserFacade.class.getName());

	protected ModelService modelService;
	protected I18NService i18nService;
	protected UserConverter userConverter;
	protected UserService userService;
	protected SessionService sessionService;
	protected AuthenticationService authenticationService;
	private BTGResultService btgResultService;

	@Override
	public UserData registerUser(final RegistrationInfo info)
	{
		final CustomerModel customerModel = new CustomerModel();
		try
		{
			customerModel.setUid(info.getLogin());
			customerModel.setName(info.getLogin());
			final AddressModel address = new AddressModel();
			address.setOwner(customerModel);
			address.setCompany(info.getCompany());
			address.setStreetname(info.getStreet());
			address.setStreetnumber(info.getStreetnumber());
			address.setFirstname(info.getFirstname());
			address.setLastname(info.getLastname());
			address.setPostalcode(info.getZip());
			address.setTown(info.getCity());
			address.setEmail(info.getMail());
			final CountryModel country = i18nService.getCountry(info.getCountry());
			if (country == null)
			{
				address.setCountry(country);
			}
			modelService.save(address);
			final Collection<AddressModel> addresses = new ArrayList<AddressModel>();
			addresses.add(address);
			customerModel.setAddresses(addresses);
			modelService.save(customerModel);
			userService.setPassword(info.getLogin(), info.getPassword());
		}
		catch (final Exception e)
		{
			LOG.error("exception occured when registering user: " + e.getMessage());
			return null;
		}
		return userConverter.convert(customerModel);
	}

	@Override
	public boolean login(final UserData user)
	{
		final User formerUser = modelService.getSource(userService.getCurrentUser());
		if (formerUser == null)
		{
			return false;
		}
		final UserModel currentUser = userService.getUser(user.getLogin());
		if (formerUser.isAnonymousCustomer())
		{
			final BTGEvaluationContext context = sessionService
					.getAttribute(SessionBTGEvaluationContextProvider.BTG_CURRENT_EVALUATION_CONTEXT);
			if (context != null)
			{
				btgResultService.moveSessionResults(currentUser, context, currentUser, context);
			}
		}
		userService.setCurrentUser(currentUser);
		return true;
	}

	@Override
	public boolean isLoggedIn()
	{
		final User user = JaloSession.getCurrentSession().getUser();
		if (user == null)
		{
			return false;
		}
		return !user.isAnonymousCustomer();
	}

	@Override
	public boolean checkCredentials(final String login, final String password)
	{
		if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password))
		{
			return false;
		}
		UserModel user = null;
		try
		{
			user = authenticationService.checkCredentials(login, password);
		}
		catch (final InvalidCredentialsException e)
		{
			return false;
		}
		return user != null;
	}

	@Override
	public boolean userExists(final String login)
	{
		return getUser(login) != null;
	}

	@Override
	public UserData getUser(final String login)
	{
		if (StringUtils.isEmpty(login))
		{
			return null;
		}
		try
		{
			return userConverter.convert(userService.getUser(login));
		}
		catch (final Exception e)
		{
			return null;
		}
	}

	@Override
	public UserData getUser()
	{
		try
		{
			return userConverter.convert(userService.getCurrentUser());
		}
		catch (final Exception e)
		{
			return null;
		}
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public void setUserConverter(final UserConverter userConverter)
	{
		this.userConverter = userConverter;
	}

	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	public void setAuthenticationService(final AuthenticationService authenticationService)
	{
		this.authenticationService = authenticationService;
	}

	public void setI18nService(final I18NService service)
	{
		i18nService = service;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	public void setBtgResultService(final BTGResultService btgResultService)
	{
		this.btgResultService = btgResultService;
	}
}
