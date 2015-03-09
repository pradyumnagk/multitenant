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
import com.clients.web.data.WishlistEntryData;
import de.hybris.platform.wishlist2.enums.Wishlist2EntryPriority;
import de.hybris.platform.wishlist2.model.Wishlist2EntryModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class WishlistEntryConverter implements Converter<Wishlist2EntryModel, WishlistEntryData>
{

	protected ProductConverter productConverter;

	public List<WishlistEntryData> convertAll(final Collection<Wishlist2EntryModel> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<WishlistEntryData> result = new ArrayList<WishlistEntryData>(sources.size());
		for (final Wishlist2EntryModel source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	@Override
	public WishlistEntryData convert(final Wishlist2EntryModel source) throws ConversionException
	{
		return convert(source, new WishlistEntryData());
	}

	@Override
	public WishlistEntryData convert(final Wishlist2EntryModel source, final WishlistEntryData prototype)
			throws ConversionException
	{
		prototype.setAddedDate(source.getAddedDate());
		prototype.setComment(source.getComment());
		int i = (source.getDesired() == null) ? 1 : source.getDesired().intValue();
		prototype.setDesired(i);
		i = (source.getReceived() == null) ? 0 : source.getReceived().intValue();
		prototype.setReceived(i);
		final Wishlist2EntryPriority p = source.getPriority();
		prototype.setPriority((p == null) ? "" : p.getCode());
		prototype.setProduct(productConverter.convert(source.getProduct()));
		return prototype;
	}

	public void setProductConverter(final ProductConverter productConverter)
	{
		this.productConverter = productConverter;
	}

}
