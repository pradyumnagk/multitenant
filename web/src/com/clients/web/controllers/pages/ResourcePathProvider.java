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


/**
 * Provides resource path for page controllers
 */
public interface ResourcePathProvider
{
	String getResourcePath(final HttpServletRequest httpServletRequest);
}
