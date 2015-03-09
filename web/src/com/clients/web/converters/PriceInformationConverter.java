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
package com.clients.web.converters;

import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.PriceData;
import com.clients.web.helpers.PriceFormatterHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PriceInformationConverter implements Converter<PriceInformation, PriceData>
{

	protected PriceFormatterHelper priceFormatterHelper;

	Collection<PriceData> convertAll(final Collection<PriceInformation> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<PriceData> prices = new ArrayList<PriceData>();
		for (final PriceInformation source : sources)
		{
			prices.add(convert(source));
		}
		Collections.sort(prices, new Comparator<PriceData>()
		{
			@Override
			public int compare(final PriceData o1, final PriceData o2)
			{
				return (o1.getValue() > o2.getValue()) ? 1 : -1;
			}
		});

		return prices;
	}

	@Override
	public PriceData convert(final PriceInformation source) throws ConversionException
	{
		return convert(source, new PriceData());
	}

	@Override
	public PriceData convert(final PriceInformation source, final PriceData prototype) throws ConversionException
	{

		prototype.setFormattedPrice(priceFormatterHelper.formatPriceValueForCurrentCurrency(source.getPriceValue().getValue()));
		prototype.setCurrencyIso(source.getPriceValue().getCurrencyIso());
		prototype.setValue(source.getPriceValue().getValue());
		prototype.setQuantity(((Long) source.getQualifierValue(PriceRow.MINQTD)).longValue());
		return prototype;
	}

	public void setPriceFormatterHelper(final PriceFormatterHelper priceFormatterHelper)
	{
		this.priceFormatterHelper = priceFormatterHelper;
	}
}
