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

import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import com.clients.web.data.CategoryData;
import com.clients.web.data.ProductData;

import javax.servlet.http.HttpServletRequest;



public interface URLHelper
{

	public static final String CATALOG = "catalog";
	public static final String CATEGORY = "category";
	public static final String CONTENT = "content";
	public static final String PRODUCT = "product";

	String getURLForCatalogPage(String catalogId);

	String getURLForCatalogPage(CatalogModel catalog);

	String getURLForCategoryPage(CategoryData category);

	String getURLForContentPage(ContentPageModel contentPage);

	String getURLForContentPage(String labelOrId);

	String getURLForProductPage(ProductData product);

	String getCurrentURL(HttpServletRequest request);

}
