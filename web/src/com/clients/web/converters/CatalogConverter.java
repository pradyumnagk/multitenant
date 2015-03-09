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

import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.ConversionHelper;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.CatalogData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class CatalogConverter implements Converter<CatalogModel, CatalogData>
{

	public Collection<CatalogData> convertAll(final Collection<CatalogModel> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<CatalogData> result = new ArrayList<CatalogData>(sources.size());
		for (final CatalogModel source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	@Override
	public CatalogData convert(final CatalogModel source) throws ConversionException
	{
		return convert(source, new CatalogData());
	}

	@Override
	public CatalogData convert(final CatalogModel source, final CatalogData prototype) throws ConversionException
	{
		ConversionHelper.copyProperties(source, prototype);
		prototype.setId(source.getId());
		return prototype;
	}

}
