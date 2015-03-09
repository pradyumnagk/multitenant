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

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.ConversionHelper;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.CategoryData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class CategoryConverter implements Converter<CategoryModel, CategoryData>
{

	public Collection<CategoryData> convertAll(final Collection<CategoryModel> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<CategoryData> result = new ArrayList<CategoryData>(sources.size());
		for (final CategoryModel source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	@Override
	public CategoryData convert(final CategoryModel source) throws ConversionException
	{
		return convert(source, new CategoryData());
	}

	@Override
	public CategoryData convert(final CategoryModel source, final CategoryData prototype) throws ConversionException
	{
		ConversionHelper.copyProperties(source, prototype);
		prototype.setCatalogId(source.getCatalogVersion().getCatalog().getId());
		return prototype;
	}

}
