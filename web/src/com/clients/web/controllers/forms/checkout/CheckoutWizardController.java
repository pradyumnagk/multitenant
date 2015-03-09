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
package com.clients.web.controllers.forms.checkout;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.order.InvalidCartException;
import com.clients.web.data.AddressData;
import com.clients.web.data.DebitPaymentData;
import com.clients.web.data.UserData;
import com.clients.web.facades.CartFacade;
import com.clients.web.facades.UserFacade;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;


public class CheckoutWizardController extends AbstractWizardFormController
{

	protected final static Logger LOG = Logger.getLogger(CheckoutWizardController.class.getName());

	protected String successView;

	protected CMSSiteService cmsSiteService;
	protected CartFacade cartFacade;
	protected UserFacade userFacade;

	@Override
	protected Map referenceData(final HttpServletRequest request, final Object command, final Errors errors, final int page)
			throws Exception
	{
		LOG.debug("referenceData called...");
		Map<String, Object> model = super.referenceData(request, command, errors, page);
		if (model == null)
		{
			model = new HashMap<String, Object>();
		}
		prepareModel(model, request);
		if (userFacade.isLoggedIn())
		{
			final CheckoutItemInfo info = (CheckoutItemInfo) command;
			final UserData user = userFacade.getUser();
			copyUserData(user, info);
		}
		return model;
	}

	@Override
	protected int getInitialPage(final HttpServletRequest request, final Object command)
	{
		if (userFacade.isLoggedIn())
		{
			LOG.debug("user is logged in. Passing to next page.");
			return 1;
		}
		return super.getInitialPage(request, command);
	}

	@Override
	protected void validatePage(final Object command, final Errors errors, final int page, final boolean finish)
	{
		LOG.debug("valdating page: " + page);
		final CheckoutItemInfo info = (CheckoutItemInfo) command;
		switch (page)
		{
			case 0:
				if (!userFacade.isLoggedIn())
				{
					rejectIfEmptyOrWhitespace(errors, "login", "login.login.empty");
					rejectIfEmptyOrWhitespace(errors, "password", "login.password.empty");
					if (!errors.hasErrors() && !userFacade.checkCredentials(info.getLogin(), info.getPassword()))
					{
						errors.reject("credentials", "login.invalid");
					}
					if (!errors.hasErrors())
					{
						LOG.debug("Logging in user...");
						UserData user = null;
						user = userFacade.getUser(info.getLogin());
						userFacade.login(user);
					}
				}
				break;
			case 1:
				rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
				rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
				rejectIfEmptyOrWhitespace(errors, "streetname", "empty");
				rejectIfEmptyOrWhitespace(errors, "streetnumber", "empty");
				rejectIfEmptyOrWhitespace(errors, "postalcode", "empty");
				rejectIfEmptyOrWhitespace(errors, "city", "empty");
				rejectIfEmptyOrWhitespace(errors, "accountNumber", "empty");
				rejectIfEmptyOrWhitespace(errors, "bank", "empty");
				rejectIfEmptyOrWhitespace(errors, "bankIDNumber", "empty");
				rejectIfEmptyOrWhitespace(errors, "baOwner", "empty");
				break;
			default:
				break;
		}
	}

	@Override
	protected ModelAndView processFinish(final HttpServletRequest request, final HttpServletResponse response,
			final Object command, final BindException errors) throws Exception
	{
		LOG.debug("processFinish called");
		final Map<String, Object> model = new HashMap<String, Object>();
		prepareModel(model, request);

		final CheckoutItemInfo info = (CheckoutItemInfo) command;
		final DebitPaymentData payment = createPaymentData(info);
		final AddressData address = createAddressData(info);

		try
		{
			final String orderId = cartFacade.placeOrder(address, payment);
			model.put("orderId", orderId);
		}
		catch (final InvalidCartException e)
		{
			model.put("error", e.getLocalizedMessage());
		}
		return new ModelAndView(successView, model);
	}

	private void prepareModel(final Map<String, Object> model, final HttpServletRequest request)
	{
		model.put("site", cmsSiteService.getCurrentSite());
		model.put("contextPath", request.getContextPath());
		model.put("cartData", cartFacade.getCurrentCart());
	}

	private void copyUserData(final UserData user, final CheckoutItemInfo info)
	{
		if (user != null && user.getAddressData() != null)
		{
			final AddressData data = user.getAddressData();
			info.setFirstname(data.getFirstname());
			info.setLastname(data.getLastname());
			info.setStreetname(data.getStreetname());
			info.setStreetnumber(data.getStreetnumber());
			info.setPostalcode(data.getPostalcode());
			info.setCity(data.getCity());
		}
	}

	private DebitPaymentData createPaymentData(final CheckoutItemInfo info)
	{
		final DebitPaymentData data = new DebitPaymentData();
		data.setAccountNumber(info.getAccountNumber());
		data.setBank(info.getBank());
		data.setBankIDNumber(info.getBankIDNumber());
		data.setBaOwner(info.getBaOwner());
		return data;
	}

	private AddressData createAddressData(final CheckoutItemInfo info)
	{
		final AddressData data = new AddressData();
		data.setFirstname(info.getFirstname());
		data.setLastname(info.getLastname());
		data.setStreetname(info.getStreetname());
		data.setStreetnumber(info.getStreetnumber());
		data.setPostalcode(info.getPostalcode());
		data.setCity(info.getCity());
		return data;
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}

	public void setSuccessView(final String successView)
	{
		this.successView = successView;
	}

}
