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
package com.clients.web.data;

import java.io.Serializable;


public class ErrorData implements Serializable
{

	private String code;
	private String defaultMessage;
	private Object[] arguments;

	public String getCode()
	{
		return code;
	}

	public void setCode(final String code)
	{
		this.code = code;
	}

	public String getDefaultMessage()
	{
		return defaultMessage;
	}

	public void setDefaultMessage(final String defaultMessage)
	{
		this.defaultMessage = defaultMessage;
	}

	public Object[] getArguments()
	{
		return arguments;
	}

	public void setArguments(final Object[] arguments)
	{
		this.arguments = arguments;
	}

}
