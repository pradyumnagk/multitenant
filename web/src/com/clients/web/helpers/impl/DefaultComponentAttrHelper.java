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

import de.hybris.platform.core.model.product.ProductModel;
import com.clients.web.helpers.ComponentAttrHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class DefaultComponentAttrHelper extends AbstractHelper implements ComponentAttrHelper
{
	final static private Logger LOG = Logger.getLogger(DefaultComponentAttrHelper.class.getName());

	public static final String COMPONENT_ATTRIBUTES_KEY = "componentAttributes";
	public static final String PRODUCT_LIST_KEY = "product";


	public void createComponentAttributes(final HttpServletRequest request)
	{
		final Map<String, Object> componentAttributes = new HashMap<String, Object>();
		request.setAttribute(COMPONENT_ATTRIBUTES_KEY, componentAttributes);
	}

	public void setComponentAttribute(final HttpServletRequest request, final String key, final Object value)
	{
		final Map<String, Object> componentAttributes = (Map<String, Object>) request.getAttribute(COMPONENT_ATTRIBUTES_KEY);

		if (componentAttributes == null)
		{
			LOG.warn("The attribute '" + COMPONENT_ATTRIBUTES_KEY + "' is not available in the request.");
		}
		else
		{
			componentAttributes.put(key, value);
		}
	}

	public Object getComponentAttribute(final HttpServletRequest request, final String key)
	{
		final Map<String, Object> componentAttributes = (Map<String, Object>) request.getAttribute(COMPONENT_ATTRIBUTES_KEY);

		if (componentAttributes == null)
		{
			LOG.warn("The attribute '" + COMPONENT_ATTRIBUTES_KEY + "' is not available in the request.");
			return null;
		}
		return componentAttributes.get(key);
	}

	public void setProductList(final HttpServletRequest request, final List<ProductModel> value)
	{
		setComponentAttribute(request, PRODUCT_LIST_KEY, value);
	}

	public List<ProductModel> getProductList(final HttpServletRequest request)
	{
		if (getComponentAttribute(request, PRODUCT_LIST_KEY) instanceof List<?>)
		{
			return (List<ProductModel>) getComponentAttribute(request, PRODUCT_LIST_KEY);
		}
		return null;
	}

}
