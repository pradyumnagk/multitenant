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

import de.hybris.platform.core.model.product.ProductModel;
import com.clients.web.controllers.pages.PageSourceObjectProvider;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.ProductHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProductPageSourceObjectProvider implements PageSourceObjectProvider<ProductModel>
{
	private ProductHelper productHelper;
	private CatalogHelper catalogHelper;

	@Override
	public ProductModel getPageSourceObject(final String resourcePath, final HttpServletRequest request,
			final HttpServletResponse response)
	{
		final String productCode = productHelper.extractProductCode(resourcePath);
		final String catalogId = catalogHelper.extractCatalogId(resourcePath);
		return productHelper.getProduct(catalogId, productCode);
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}
}
