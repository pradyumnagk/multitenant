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
package com.clients.web.interceptors;

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.servicelayer.i18n.I18NService;
import com.clients.web.facades.CartFacade;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class CurrencyChangeInterceptor extends AbstractInterceptor
{
	protected final static Logger LOG = Logger.getLogger(CurrencyChangeInterceptor.class.getName());

	public final static String CURRENCYISOCODE_PARA = "currencyIsoCode";

	protected I18NService i18nService;
	protected CartFacade cartFacade;

	@Override
	public boolean doPreHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final String actionName) throws ServletException
	{
		//check if there is a (new) currencyIsoCode parameter in the request 
		final String isoCode = request.getParameter(CURRENCYISOCODE_PARA);

		if (isoCode != null)
		{
			for (final CurrencyModel currencyModel : i18nService.getAllCurrencies())
			{
				if (StringUtils.equals(currencyModel.getIsocode(), isoCode))
				{
					i18nService.setCurrentCurrency(currencyModel);
					// after currency change cart must be recalculated
					cartFacade.calculateCart();
					return true;
				}
			}
		}

		return true;
	}

	public void setI18nService(final I18NService service)
	{
		i18nService = service;
	}

	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

}
