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
import com.clients.web.data.ErrorData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.validation.ObjectError;


public class ErrorConverter implements Converter<ObjectError, ErrorData>
{

	public Collection<ErrorData> convertAll(final Collection<ObjectError> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<ErrorData> result = new ArrayList<ErrorData>();
		for (final ObjectError source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	@Override
	public ErrorData convert(final ObjectError source) throws ConversionException
	{
		return convert(source, new ErrorData());
	}

	@Override
	public ErrorData convert(final ObjectError source, final ErrorData prototype) throws ConversionException
	{
		prototype.setCode(source.getCode());
		prototype.setDefaultMessage(source.getDefaultMessage());
		prototype.setArguments(source.getArguments());
		return prototype;
	}

}
