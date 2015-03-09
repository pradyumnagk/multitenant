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
package com.clients.web.helpers.impl;

import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import com.clients.web.helpers.PriceFormatterHelper;


public class DefaultPriceFormatterHelper implements PriceFormatterHelper
{
	protected ModelService modelService;
	protected I18NService i18nService;

	@Override
	public String formatPriceValueForCurrentCurrency(final Double priceValue)
	{
		if (priceValue == null)
		{
			return "";
		}
		return formatPriceValueForCurrentCurrency(priceValue.doubleValue());
	}

	@Override
	public String formatPriceValueForCurrentCurrency(final double priceValue)
	{
		//TODO no switch to JALO
		final Currency currency = modelService.getSource(i18nService.getCurrentCurrency());
		return currency.format(priceValue);
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public void setI18nService(final I18NService service)
	{
		i18nService = service;
	}


}
