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
package com.clients.web.facades.impl;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import com.clients.web.converters.CategoryConverter;
import com.clients.web.data.CategoryData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.helpers.CategoryHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;


public class DefaultCategoryFacade implements CategoryFacade
{

	protected final static Logger LOG = Logger.getLogger(DefaultCategoryFacade.class.getName());

	protected CMSSiteService cmsSiteService;
	protected CatalogService catalogService;
	protected CategoryService categoryService;
	protected CategoryConverter categoryConverter;
	protected CategoryHelper categoryHelper;

	protected CatalogVersionModel getCatalogVersion(final String catalogId) throws UnknownIdentifierException
	{
		return categoryHelper.getCatalogVersion(catalogId);
	}

	@Override
	public CategoryData getCategory(final String catalogId, final List<String> categoryPath)
	{
		final CategoryModel category = categoryHelper.getCategory(catalogId, categoryPath);
		return getCategory(category, categoryPath);
	}

	public CategoryData getCategory(final CategoryModel category, final List<String> categoryPath)
	{
		if (category == null)
		{
			return null;
		}

		final CategoryData catData = categoryConverter.convert(category);
		final List<String> path = new ArrayList<String>(categoryPath);
		catData.setPath(path);
		return catData;
	}

	@Override
	public CategoryData getCategory(final String catalogId, final List<String> superCategoryPath, final String categoryCode)
	{
		final List<String> path = new ArrayList<String>(superCategoryPath);
		path.add(categoryCode);
		return getCategory(catalogId, path);
	}

	@Override
	public List<CategoryData> getRootCategories()
	{
		final CatalogVersionModel cv = cmsSiteService.getCurrentCatalogVersion();
		if (cv == null)
		{
			LOG.warn("Current CatalogVersion is null!");
			return Collections.EMPTY_LIST;
		}
		final List<CategoryData> childs = new ArrayList<CategoryData>();
		for (final CategoryModel category : cv.getRootCategories())
		{
			final CategoryData child = categoryConverter.convert(category);
			final List<String> path = new ArrayList<String>();
			path.add(child.getCode());
			child.setPath(path);
			childs.add(child);
		}
		return childs;
	}

	@Override
	public List<CategoryData> getSubCategories(final CategoryData parentCategory)
	{
		if (parentCategory == null)
		{
			return Collections.EMPTY_LIST;
		}
		final CategoryModel parent = getCategory(parentCategory);
		if (parent == null)
		{
			LOG.warn("Could not find category [" + parentCategory.getCode() + "]");
			return Collections.EMPTY_LIST;
		}
		final List<CategoryData> childs = new ArrayList<CategoryData>();
		for (final CategoryModel category : parent.getCategories())
		{
			final CategoryData child = categoryConverter.convert(category);
			final List<String> path = new ArrayList<String>(parentCategory.getPath());
			path.add(child.getCode());
			child.setPath(path);
			childs.add(child);
		}
		return childs;
	}

	@Override
	public boolean hasSubCategories(final CategoryData category)
	{
		final CategoryModel cat = getCategory(category);
		return !cat.getCategories().isEmpty();
	}

	@Override
	public CategoryData getParentCategory(final CategoryData category)
	{
		final List<String> path = category.getPath();
		if (path == null || path.size() < 2)
		{
			return null;
		}
		return getCategory(category.getCatalogId(), path.subList(0, path.size() - 1));
	}

	@Override
	public List<String> getPathForCategory(final CategoryData category)
	{
		final List<String> result = new ArrayList<String>();
		final String catalogId = category.getCatalogId();
		final CategoryModel source = categoryService.getCategory(getCatalogVersion(catalogId), category.getCode());
		Collection<CategoryModel> parents = source.getSupercategories();
		result.add(category.getCode());
		if (parents == null || parents.isEmpty())
		{
			return result;
		}
		while (!parents.isEmpty())
		{
			parents = filterParents(parents, catalogId);
			if (parents.isEmpty())
			{
				return result;
			}
			if (parents.size() > 1)
			{
				LOG.warn("Category [" + category.getCode()
						+ "] is in more than one category. Taking the first one. This may be not the expected result!");
				if (LOG.isDebugEnabled())
				{
					String d = "";
					for (final CategoryModel c : parents)
					{
						d += c.getCode() + ";";
					}
					LOG.debug("supercategories: [" + d + "]");
				}
			}
			final CategoryModel cat = parents.iterator().next();
			result.add(0, cat.getCode());
			parents = cat.getSupercategories();
		}
		return result;
	}

	private Collection<CategoryModel> filterParents(final Collection<CategoryModel> parents, final String catalogId)
	{
		final List<CategoryModel> result = new ArrayList<CategoryModel>();
		for (final CategoryModel cat : parents)
		{
			if (cat.getCatalogVersion().getCatalog().getId().equals(catalogId))
			{
				result.add(cat);
			}
		}
		return result;
	}

	@Override
	public CategoryData getCategory(final CategoryModel category)
	{
		final CategoryData data = categoryConverter.convert(category);
		data.setPath(getPathForCategory(data));
		return data;
	}

	@Override
	public CategoryData getCategory(final String code)
	{
		try
		{
			final CategoryModel categoryModel = categoryService.getCategory(code);
			return getCategory(categoryModel);
		}
		catch (final SystemException systemException)
		{

			LOG.debug("Cannot find a category with code: " + code, systemException);
		}

		return null;
	}

	private CategoryModel getCategory(final CategoryData category)
	{
		return categoryService.getCategory(getCatalogVersion(category.getCatalogId()), category.getCode());
	}


	public void setCatalogService(final CatalogService catalogService)
	{
		this.catalogService = catalogService;
	}

	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	public void setCategoryConverter(final CategoryConverter categoryConverter)
	{
		this.categoryConverter = categoryConverter;
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}
}
