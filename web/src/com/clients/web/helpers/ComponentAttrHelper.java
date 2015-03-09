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

import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface ComponentAttrHelper
{
	void createComponentAttributes(final HttpServletRequest request);

	void setComponentAttribute(final HttpServletRequest request, final String key, final Object value);

	Object getComponentAttribute(final HttpServletRequest request, final String key);

	void setProductList(final HttpServletRequest request, final List<ProductModel> value);

	List<ProductModel> getProductList(final HttpServletRequest request);
}
