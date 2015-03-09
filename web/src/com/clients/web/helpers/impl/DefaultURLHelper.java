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

import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import com.clients.web.data.CategoryData;
import com.clients.web.data.ProductData;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.CategoryHelper;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.ProductHelper;
import com.clients.web.helpers.URLHelper;

import javax.servlet.http.HttpServletRequest;


public class DefaultURLHelper implements URLHelper
{

	public static final String VIEW = "/view";

	protected CatalogHelper catalogHelper;
	protected CategoryHelper categoryHelper;
	protected ProductHelper productHelper;
	protected PageHelper pageHelper;

	@Override
	public String getURLForCategoryPage(final CategoryData category)
	{
		return VIEW + "/" + CATEGORY + "/" + category.getCatalogId() + "/" + category.getPathAsString();
	}

	@Override
	public String getURLForCatalogPage(final String catalogId)
	{
		return VIEW + "/" + CATALOG + "/" + catalogId;
	}

	@Override
	public String getURLForCatalogPage(final CatalogModel catalog)
	{
		return getURLForCatalogPage(catalog.getId());
	}

	@Override
	public String getURLForContentPage(final ContentPageModel contentPage)
	{
		return VIEW + "/" + CONTENT + "/" + contentPage.getLabelOrId();
	}

	@Override
	public String getURLForProductPage(final ProductData product)
	{
		return VIEW + "/" + PRODUCT + "/" + product.getCatalogId() + "/" + product.getPathAsString();
	}

	@Override
	public String getURLForContentPage(final String labelOrId)
	{
		return VIEW + "/" + CONTENT + "/" + labelOrId;
	}

	@Override
	public String getCurrentURL(final HttpServletRequest request)
	{
		final AbstractPageModel page = pageHelper.getCurrentPage(request);
		if (page != null && pageHelper.isCurrentPageContentPage(request))
		{
			return getURLForContentPage((ContentPageModel) page);
		}
		final ProductData product = productHelper.getCurrentProduct(request);
		if (product != null)
		{
			return getURLForProductPage(product);
		}
		final CategoryData category = categoryHelper.getCurrentCategory(request);
		if (category != null)
		{
			return getURLForCategoryPage(category);
		}
		final CatalogModel catalog = catalogHelper.getCurrentCatalogVersion().getCatalog();
		if (catalog != null)
		{
			return getURLForCatalogPage(catalog.getId());
		}
		return null;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}

}
