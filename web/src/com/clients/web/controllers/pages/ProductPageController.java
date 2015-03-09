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

import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.core.model.product.ProductModel;
import com.clients.web.data.CategoryData;
import com.clients.web.data.ProductData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.facades.ProductFacade;
import com.clients.web.helpers.CategoryHelper;
import com.clients.web.helpers.ProductHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ProductPageController extends AbstractPageController
{

	protected CategoryHelper categoryHelper;
	protected ProductHelper productHelper;
	protected ProductFacade productFacade;
	protected CategoryFacade categoryFacade;
	protected PageSourceObjectProvider<ProductModel> pageSourceObjectProvider;

	@Override
	protected AbstractPageModel getPage(final String resourcePath, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{
		final ProductModel productModel = pageSourceObjectProvider.getPageSourceObject(resourcePath, request, response);

		final String productCode = productHelper.extractProductCode(resourcePath);
		final String catalogId = catalogHelper.extractCatalogId(resourcePath);

		final List<String> categoryPath = productHelper.extractCategoryPath(resourcePath);
		ProductData product = null;
		if (productCode != null)
		{
			product = productFacade.getProduct(productModel, categoryPath);
		}

		catalogHelper.setCurrentCatalogVersionById(catalogId);
		final CategoryData category = categoryFacade.getCategory(catalogId, categoryPath);
		categoryHelper.setCurrentCategory(request, category);
		productHelper.setCurrentProduct(request, product);

		return cmsPageService.getPageByProductCode(productCode);
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	public void setCategoryFacade(final CategoryFacade categoryFacade)
	{
		this.categoryFacade = categoryFacade;
	}

	public void setPageSourceObjectProvider(final PageSourceObjectProvider<ProductModel> pageSourceObjectProvider)
	{
		this.pageSourceObjectProvider = pageSourceObjectProvider;
	}
}
