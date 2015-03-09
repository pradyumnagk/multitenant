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

import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.CustomerReviewData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CustomerReviewConverter implements Converter<CustomerReviewModel, CustomerReviewData>
{

	public Collection<CustomerReviewData> convertAll(final Collection<CustomerReviewModel> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<CustomerReviewData> reviews = new ArrayList<CustomerReviewData>(sources.size());
		for (final CustomerReviewModel source : sources)
		{
			reviews.add(convert(source));
		}
		Collections.sort(reviews, new Comparator<CustomerReviewData>()
		{

			@Override
			public int compare(final CustomerReviewData o1, final CustomerReviewData o2)
			{
				return (o1.getRating() > o2.getRating()) ? 1 : -1;
			}

		});
		return reviews;
	}

	@Override
	public CustomerReviewData convert(final CustomerReviewModel source) throws ConversionException
	{
		return convert(source, new CustomerReviewData());
	}

	@Override
	public CustomerReviewData convert(final CustomerReviewModel source, final CustomerReviewData prototype)
			throws ConversionException
	{
		prototype.setHeadline(source.getHeadline());
		prototype.setComment(source.getComment());
		prototype.setUser(source.getUser());
		if (source.getRating() != null)
		{
			final Double rating = source.getRating();
			if (rating != null)
			{
				prototype.setRating(Math.round(rating.doubleValue()));
			}
			else
			{
				prototype.setRating(0);
			}

		}
		else
		{
			prototype.setRating(0);
		}
		prototype.setDate(source.getCreationtime());
		return prototype;
	}

}
