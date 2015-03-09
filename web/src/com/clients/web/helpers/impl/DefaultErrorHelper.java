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

import com.clients.web.data.ErrorData;
import com.clients.web.helpers.ErrorHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


public class DefaultErrorHelper implements ErrorHelper
{

	protected final static String ERRORS = "ERRORS";

	@Override
	public void addErrors(final HttpServletRequest request, final Collection<ErrorData> errors)
	{
		Collection<ErrorData> e = getErrors(request);
		if (e == null)
		{
			e = new ArrayList<ErrorData>();
		}
		e.addAll(errors);
		request.getSession().setAttribute(ERRORS, e);
	}

	@Override
	public ErrorData getError(final HttpServletRequest request, final String errorId)
	{
		for (final ErrorData error : getErrors(request))
		{
			if (error.getCode().equals(errorId))
			{
				return error;
			}
		}
		return null;
	}

	@Override
	public Collection<ErrorData> getErrors(final HttpServletRequest request)
	{
		final List<ErrorData> errors = (List<ErrorData>) request.getSession().getAttribute(ERRORS);
		return (errors == null) ? new ArrayList<ErrorData>() : errors;
	}

	@Override
	public void removeError(final HttpServletRequest request, final String errorId)
	{
		final Collection<ErrorData> errors = getErrors(request);
		ErrorData e = null;
		for (final ErrorData error : errors)
		{
			if (error.getCode().equals(errorId))
			{
				e = error;
				break;
			}
		}
		if (e != null)
		{
			errors.remove(e);
			setErrors(request, errors);
		}
	}

	@Override
	public void setErrors(final HttpServletRequest request, final Collection<ErrorData> errors)
	{
		request.getSession().setAttribute(ERRORS, errors);
	}


}
