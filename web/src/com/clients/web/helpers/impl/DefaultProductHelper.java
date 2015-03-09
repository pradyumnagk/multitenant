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

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import com.clients.web.data.ProductData;
import com.clients.web.helpers.ProductHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class DefaultProductHelper extends AbstractHelper implements ProductHelper
{
	protected final static Logger LOG = Logger.getLogger(DefaultProductHelper.class.getName());

	public static final String PRODUCT_KEY = "currentProduct";
	public static final String PRODUCT_CODE_KEY = "currentProductCode";

	protected ProductService productService;

	@Override
	public String extractProductCode(final String path)
	{
		final String[] components = getPathComponents(path);
		if (components != null && components.length > 0)
		{
			return components[components.length - 1];
		}
		else
		{
			return null;
		}
	}

	@Override
	public List<String> extractCategoryPath(final String path)
	{
		final String[] components = getPathComponents(path);
		if (components != null && components.length > 2)
		{
			return Arrays.asList((String[]) subarray(components, 1, components.length - 1));
		}
		else
		{
			return Collections.emptyList();
		}
	}

	@Override
	public ProductData getCurrentProduct(final HttpServletRequest request)
	{
		return (ProductData) request.getAttribute(PRODUCT_KEY);
	}

	@Override
	public void setCurrentProduct(final HttpServletRequest request, final ProductData product)
	{
		request.setAttribute(PRODUCT_KEY, product);
		if (product != null)
		{
			request.setAttribute(PRODUCT_CODE_KEY, product.getCode());
		}
	}

	/**
	 * Helper method used to expose logic that converts tuple {catalogId,productCode} -> ProductModel
	 */
	@Override
	public ProductModel getProduct(final String catalogId, final String productCode)
	{
		if (productCode == null)
		{
			return null;
		}
		else
		{
			String code = productCode;
			if (code.contains("/"))
			{
				code = StringUtils.substringAfterLast(code, "/");
			}
			return productService.getProduct(getCatalogVersion(catalogId), code);
		}
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}
}
