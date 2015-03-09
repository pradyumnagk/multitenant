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

import de.hybris.platform.cms2.model.contents.components.SimpleCMSComponentModel;
import com.clients.web.data.PaginationData;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



public interface PaginationHelper
{

	PaginationData getPaginationData(HttpServletRequest request, SimpleCMSComponentModel component);

	PaginationData getPaginationDataForSearch(HttpServletRequest request);

	List<String> getPaginationSizes();

	List<String> getOrderKeys();

}
