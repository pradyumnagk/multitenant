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

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import com.clients.web.data.CategoryData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.helpers.CategoryHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CategoryPageController extends AbstractPageController
{
	protected CategoryHelper categoryHelper;
	protected CategoryFacade categoryFacade;
	protected PageSourceObjectProvider<CategoryModel> pageSourceObjectProvider;

	@Override
	protected AbstractPageModel getPage(final String resourcePath, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{
		final CategoryModel catModel = pageSourceObjectProvider.getPageSourceObject(resourcePath, request, response);

		final String catalogId = catalogHelper.extractCatalogId(resourcePath);
		final List<String> categoryPath = categoryHelper.extractCategoryPath(resourcePath);

		CategoryData category = null;
		if (categoryPath != null && !categoryPath.isEmpty())
		{
			category = categoryFacade.getCategory(catModel, categoryPath);
		}

		catalogHelper.setCurrentCatalogVersionById(catalogId);
		categoryHelper.setCurrentCategory(request, category);
		return cmsPageService.getPageByCategoryCode(category.getCode());
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setCategoryFacade(final CategoryFacade categoryFacade)
	{
		this.categoryFacade = categoryFacade;
	}

	public void setPageSourceObjectProvider(final PageSourceObjectProvider<CategoryModel> pageSourceObjectProvider)
	{
		this.pageSourceObjectProvider = pageSourceObjectProvider;
	}
}
