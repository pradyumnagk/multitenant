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

import de.hybris.platform.cms2.model.pages.AbstractPageModel;

import javax.servlet.http.HttpServletRequest;


public interface PageHelper
{

	void setCurrentPage(HttpServletRequest request, AbstractPageModel page);

	AbstractPageModel getCurrentPage(HttpServletRequest request);

	boolean isCurrentPageContentPage(HttpServletRequest request);

	String extractPageLabelOrId(final String path);

	AbstractPageModel getPageByLabelOrIdSafe(String labelOrId);
}
