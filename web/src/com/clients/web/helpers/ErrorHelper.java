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
package com.clients.web.helpers;

import com.clients.web.data.ErrorData;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;


public interface ErrorHelper
{

	void addErrors(HttpServletRequest request, Collection<ErrorData> errors);

	Collection<ErrorData> getErrors(HttpServletRequest request);

	ErrorData getError(HttpServletRequest request, String errorId);

	void removeError(HttpServletRequest request, String errorId);

	void setErrors(HttpServletRequest request, Collection<ErrorData> errors);

}
