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

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import com.clients.web.data.CategoryData;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public interface CategoryHelper
{

	void setCurrentCategory(HttpServletRequest request, CategoryData category);

	CategoryData getCurrentCategory(HttpServletRequest request);

	List<String> extractCategoryPath(String path);

	void setPath(final HttpServletRequest request, final String path);

	String getPath(final HttpServletRequest request);

	CategoryModel getCategory(String catalogId, List<String> categoryPath);

	CatalogVersionModel getCatalogVersion(final String catalogId);
}
