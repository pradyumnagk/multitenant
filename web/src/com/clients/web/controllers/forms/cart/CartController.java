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
package com.clients.web.controllers.forms.cart;

import com.clients.web.converters.ErrorConverter;
import com.clients.web.data.CartData;
import com.clients.web.data.CartEntryData;
import com.clients.web.facades.CartFacade;
import com.clients.web.helpers.ErrorHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


public class CartController extends SimpleFormController
{
	protected final static Logger LOG = Logger.getLogger(CartController.class.getName());

	protected CartFacade cartFacade;
	protected ErrorHelper errorHelper;
	protected ErrorConverter errorConverter;

	protected final static String PLUS = "plusQuantityEntry";
	protected final static String MINUS = "minusQuantityEntry";
	protected final static String DELETE = "delete";

	@Override
	protected boolean isFormSubmission(final HttpServletRequest request)
	{
		if (!"POST".equals(request.getMethod()))
		{
			return false;
		}
		final String clazz = this.getClass().getSimpleName();
		return request.getParameter(clazz) != null || request.getParameter(PLUS) != null || request.getParameter(MINUS) != null;
	}

	@Override
	protected ModelAndView processFormSubmission(final HttpServletRequest request, final HttpServletResponse response,
			final Object command, final BindException errors) throws Exception
	{
		final CartItemInfo cartInfo = (CartItemInfo) command;
		if (errors.getErrorCount() > 0)
		{
			LOG.debug("errors while processing cartForm");
			errorHelper.addErrors(request, errorConverter.convertAll(errors.getAllErrors()));
			String url = request.getContextPath() + cartInfo.getCurrentURL();
			if (!StringUtils.isEmpty(cartInfo.getBackURL()))
			{
				url += "?from=" + cartInfo.getBackURL();
			}
			response.sendRedirect(url);
			return null;
		}
		return super.processFormSubmission(request, response, command, errors);
	}

	@Override
	protected ModelAndView onSubmit(final HttpServletRequest request, final HttpServletResponse response, final Object command,
			final BindException errors) throws Exception
	{
		final CartItemInfo cartInfo = (CartItemInfo) command;
		if (cartInfo.isQuickAdd())
		{
			final int qty = Integer.parseInt(cartInfo.getQuantity());
			cartFacade.addToCart(cartInfo.getProductCode(), qty);
		}
		else
		{
			if (cartInfo.getMinusQuantityEntry() != null || cartInfo.getPlusQuantityEntry() != null)
			{
				int mod = -1;
				String entryNumber = cartInfo.getMinusQuantityEntry();
				if (cartInfo.getPlusQuantityEntry() != null)
				{
					mod = +1;
					entryNumber = cartInfo.getPlusQuantityEntry();

				}
				final CartData cart = cartFacade.getCurrentCart();
				final CartEntryData entry = cart.getEntry(Integer.parseInt(entryNumber));
				if (entry != null)
				{
					final String productCode = entry.getProduct().getCode();
					final long qty = entry.getQuantity() + mod;
					cartFacade.updateQuantity(productCode, qty);
				}
			}
			else
			{
				cartFacade.updateQuantities(convert(cartInfo.getQuantities()));
			}
		}
		String url = request.getContextPath() + cartInfo.getCurrentURL();
		if (!StringUtils.isEmpty(cartInfo.getBackURL()))
		{
			url += "?from=" + cartInfo.getBackURL();
		}
		response.sendRedirect(url);
		return null;
	}

	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		final String entry = request.getParameter(DELETE);
		if (!StringUtils.isEmpty(entry) && StringUtils.isNumeric(entry))
		{
			LOG.debug("Deleting cartItemEntry [" + entry + "]");
			cartFacade.deleteCartEntry(Integer.valueOf(entry));
			final String currentURL = request.getParameter("currentURL");
			String url = request.getContextPath() + ((currentURL != null) ? currentURL : "");
			if (!StringUtils.isEmpty(request.getParameter("backURL")))
			{
				url += "?from=" + request.getParameter("backURL");
			}
			response.sendRedirect(url);
			return null;
		}
		return super.handleRequest(request, response);
	}

	protected List<Long> convert(final List<String> quantities)
	{
		final List<Long> result = new ArrayList<Long>();
		for (final String q : quantities)
		{
			result.add(Long.valueOf(q));
		}
		return result;
	}

	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	public void setErrorConverter(final ErrorConverter errorConverter)
	{
		this.errorConverter = errorConverter;
	}

	public void setErrorHelper(final ErrorHelper errorHelper)
	{
		this.errorHelper = errorHelper;
	}

}
