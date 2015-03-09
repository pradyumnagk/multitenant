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

import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.CartEntryData;
import com.clients.web.data.ProductData;
import com.clients.web.facades.ProductFacade;
import com.clients.web.helpers.PriceFormatterHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CartEntryConverter implements Converter<AbstractOrderEntryModel, CartEntryData>
{

	protected ProductConverter productConverter;
	protected ProductFacade productFacade;
	protected PriceFormatterHelper priceFormatterHelper;

	public List<CartEntryData> convertAll(final List<AbstractOrderEntryModel> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<CartEntryData> result = new ArrayList<CartEntryData>(sources.size());
		for (final AbstractOrderEntryModel source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	@Override
	public CartEntryData convert(final AbstractOrderEntryModel source) throws ConversionException
	{
		return convert(source, new CartEntryData());
	}

	@Override
	public CartEntryData convert(final AbstractOrderEntryModel source, final CartEntryData prototype) throws ConversionException
	{
		prototype.setEntryNumber(source.getEntryNumber().intValue());
		prototype.setQuantity(source.getQuantity().longValue());
		final double basePrice = source.getBasePrice().doubleValue();
		prototype.setBasePrice(basePrice);
		prototype.setFormattedBasePrice(priceFormatterHelper.formatPriceValueForCurrentCurrency(basePrice));
		final double totalPrice = source.getTotalPrice().doubleValue();
		prototype.setTotalPrice(totalPrice);
		prototype.setFormattedTotalPrice(priceFormatterHelper.formatPriceValueForCurrentCurrency(totalPrice));
		final ProductData product = productConverter.convert(source.getProduct());
		product.setPath(productFacade.getPathForProduct(product));
		prototype.setProduct(product);
		return prototype;
	}

	public void setProductConverter(final ProductConverter productConverter)
	{
		this.productConverter = productConverter;
	}

	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	public void setPriceFormatterHelper(final PriceFormatterHelper priceFormatterHelper)
	{
		this.priceFormatterHelper = priceFormatterHelper;
	}

}
