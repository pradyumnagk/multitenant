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
package com.clients.web.controllers.pages.impl;

import de.hybris.platform.category.model.CategoryModel;
import com.clients.web.controllers.pages.PageSourceObjectProvider;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.CategoryHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CategoryPageSourceObjectProvider implements PageSourceObjectProvider<CategoryModel>
{
	private CategoryHelper categoryHelper;
	private CatalogHelper catalogHelper;

	@Override
	public CategoryModel getPageSourceObject(final String resourcePath, final HttpServletRequest request,
			final HttpServletResponse response)
	{
		final String catalogId = catalogHelper.extractCatalogId(resourcePath);
		final List<String> categoryPath = categoryHelper.extractCategoryPath(resourcePath);
		return categoryHelper.getCategory(catalogId, categoryPath);
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}
}
