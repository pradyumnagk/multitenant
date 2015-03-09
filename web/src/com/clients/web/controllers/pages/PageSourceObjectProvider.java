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
package com.clients.web.controllers.pages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Provides a source object for the page to display. For example provides ProductModel for a Page displaying Products.
 */
public interface PageSourceObjectProvider<T>
{
	T getPageSourceObject(final String resourcePath, final HttpServletRequest request, final HttpServletResponse response)
			throws Exception; //NOPMD
}
