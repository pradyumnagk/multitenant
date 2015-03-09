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

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.CartData;
import com.clients.web.helpers.PriceFormatterHelper;


public class CartConverter implements Converter<CartModel, CartData>
{

	protected CartEntryConverter cartEntryConverter;
	protected PriceFormatterHelper priceFormatterHelper;

	@Override
	public CartData convert(final CartModel source) throws ConversionException
	{
		return convert(source, new CartData());
	}

	@Override
	public CartData convert(final CartModel source, final CartData prototype) throws ConversionException
	{
		final double totalPrice = source.getTotalPrice().doubleValue();
		prototype.setTotalPrice(totalPrice);
		prototype.setFormattedTotalPrice(priceFormatterHelper.formatPriceValueForCurrentCurrency(totalPrice));
		prototype.setEntries(cartEntryConverter.convertAll(source.getEntries()));
		return prototype;
	}

	public void setCartEntryConverter(final CartEntryConverter cartEntryConverter)
	{
		this.cartEntryConverter = cartEntryConverter;
	}

	public void setPriceFormatterHelper(final PriceFormatterHelper priceFormatterHelper)
	{
		this.priceFormatterHelper = priceFormatterHelper;
	}

}
