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
import com.clients.web.data.ProductData;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface ProductHelper
{

	void setCurrentProduct(HttpServletRequest request, ProductData product);

	ProductData getCurrentProduct(HttpServletRequest request);

	String extractProductCode(String path);

	List<String> extractCategoryPath(String path);

	ProductModel getProduct(final String catalogId, final String productCode);

}
