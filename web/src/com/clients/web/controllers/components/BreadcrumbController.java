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
package com.clients.web.controllers.components;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import com.clients.web.data.CategoryData;
import com.clients.web.data.ProductData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.CategoryHelper;
import com.clients.web.helpers.ProductHelper;
import com.clients.web.helpers.URLHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class BreadcrumbController implements Controller
{

	protected final static Logger LOG = Logger.getLogger(BreadcrumbController.class.getName());

	protected String view;

	// -------------- Services -----------------------
	protected ProductHelper productHelper;
	protected CategoryHelper categoryHelper;
	protected CatalogHelper catalogHelper;
	protected CategoryFacade categoryFacade;
	protected URLHelper urlHelper;

	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		List<BreadcrumbItem> items = Collections.emptyList();

		final CatalogVersionModel catalogVersionModel = catalogHelper.getCurrentCatalogVersion();
		if (catalogVersionModel != null)
		{
			items = new ArrayList<BreadcrumbItem>();
			final BreadcrumbItem catalogBreadcrumbItem = new BreadcrumbItem(catalogVersionModel.getCatalog().getName(), urlHelper
					.getURLForCatalogPage(catalogVersionModel.getCatalog().getId()));
			items.add(catalogBreadcrumbItem);

			CategoryData categoryData = categoryHelper.getCurrentCategory(request);
			while (categoryData != null)
			{
				final BreadcrumbItem categoryBreadcrumbItem = new BreadcrumbItem(categoryData.getName(), urlHelper
						.getURLForCategoryPage(categoryData));
				items.add(1, categoryBreadcrumbItem);
				categoryData = categoryFacade.getParentCategory(categoryData);
			}

			final ProductData productData = productHelper.getCurrentProduct(request);
			if (productData != null)
			{
				final BreadcrumbItem productBreadcrumbItem = new BreadcrumbItem(productData.getName(), urlHelper
						.getURLForProductPage(productData));
				items.add(productBreadcrumbItem);
			}
		}

		model.put("breadcrumbItems", items);
		return new ModelAndView(getView(), model);
	}

	public class BreadcrumbItem
	{
		private String displayName;
		private String url;

		public BreadcrumbItem()
		{
			// default constructor
		}

		public BreadcrumbItem(final String displayName, final String url)
		{
			super();
			this.displayName = displayName;
			this.url = url;
		}

		public String getDisplayName()
		{
			return displayName;
		}

		public void setDisplayName(final String displayName)
		{
			this.displayName = displayName;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(final String url)
		{
			this.url = url;
		}
	}

	public String getView()
	{
		return view;
	}

	public void setView(final String view)
	{
		this.view = view;
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}

	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

	public void setCategoryFacade(final CategoryFacade categoryFacade)
	{
		this.categoryFacade = categoryFacade;
	}

}
