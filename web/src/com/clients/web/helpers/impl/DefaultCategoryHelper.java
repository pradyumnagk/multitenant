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
package com.clients.web.helpers.impl;

import static org.apache.commons.lang.ArrayUtils.subarray;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import com.clients.web.data.CategoryData;
import com.clients.web.helpers.CategoryHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class DefaultCategoryHelper extends AbstractHelper implements CategoryHelper
{

	protected final static Logger LOG = Logger.getLogger(DefaultCategoryHelper.class.getName());

	private static final String PATH_KEY = "path";
	public static final String CATEGORY_KEY = "currentCategory";
	public static final String CATEGORY_CODE_KEY = "currentCategoryCode";

	protected CategoryService categoryService;

	@Override
	public List<String> extractCategoryPath(final String path)
	{
		final String[] components = getPathComponents(path);
		if (components != null && components.length > 1)
		{
			return Arrays.asList((String[]) subarray(components, 1, components.length));
		}
		else
		{
			return Collections.emptyList();
		}
	}

	@Override
	public CategoryData getCurrentCategory(final HttpServletRequest request)
	{
		return (CategoryData) request.getAttribute(CATEGORY_KEY);
	}

	@Override
	public void setCurrentCategory(final HttpServletRequest request, final CategoryData category)
	{
		request.setAttribute(CATEGORY_KEY, category);
		if (category != null)
		{
			request.setAttribute(CATEGORY_CODE_KEY, category.getCode());
		}
	}

	@Override
	public String getPath(final HttpServletRequest request)
	{
		return (String) request.getAttribute(PATH_KEY);
	}

	@Override
	public void setPath(final HttpServletRequest request, final String path)
	{
		request.setAttribute(PATH_KEY, path);
	}

	@Override
	public CategoryModel getCategory(final String catalogId, final List<String> categoryPath)
	{
		if (categoryPath == null || categoryPath.isEmpty())
		{
			return null;
		}
		final CatalogVersionModel catalogVersion = this.getCatalogVersion(catalogId);
		final String categoryCode = categoryPath.get(categoryPath.size() - 1);
		return categoryService.getCategory(catalogVersion, categoryCode);
	}

	@Override
	public CatalogVersionModel getCatalogVersion(final String catalogId) //NOPMD
	{
		return super.getCatalogVersion(catalogId);
	}

	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}
}
