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

import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.WishlistData;
import com.clients.web.data.WishlistEntryData;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class WishlistConverter implements Converter<Wishlist2Model, WishlistData>
{

	protected WishlistEntryConverter wishlistEntryConverter;

	public List<WishlistData> convertAll(final Collection<Wishlist2Model> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<WishlistData> result = new ArrayList<WishlistData>(sources.size());
		for (final Wishlist2Model source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	@Override
	public WishlistData convert(final Wishlist2Model source) throws ConversionException
	{
		return convert(source, new WishlistData());
	}

	@Override
	public WishlistData convert(final Wishlist2Model source, final WishlistData prototype) throws ConversionException
	{
		prototype.setCreationTime(source.getCreationtime());
		final boolean isDefault = (source.getDefault() != null) ? source.getDefault().booleanValue() : false;
		prototype.setDefault(isDefault);
		prototype.setDescription(source.getDescription());
		final List<WishlistEntryData> entries = wishlistEntryConverter.convertAll(source.getEntries());
		if (entries != null)
		{
			prototype.setEntries(entries);
		}
		return prototype;
	}

	public void setWishlistEntryConverter(final WishlistEntryConverter wishlistEntryConverter)
	{
		this.wishlistEntryConverter = wishlistEntryConverter;
	}

}
