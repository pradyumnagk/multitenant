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
package com.clients.web.controllers.components.cms2;

import de.hybris.platform.cms2lib.model.components.ProductDetailComponentModel;
import com.clients.web.facades.ProductFacade;
import com.clients.web.helpers.ProductHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;


public class ProductDetailComponentController extends AbstractCMSComponentController<ProductDetailComponentModel>
{

	private ProductHelper productHelper;
	private ProductFacade productFacade;

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final ProductDetailComponentModel component) throws Exception
	{
		final String code = component.getProductCode();
		if (StringUtils.isEmpty(code))
		{
			return new ModelAndView(view, "productDetail", productHelper.getCurrentProduct(request));
		}
		return new ModelAndView(view, "productDetail", productFacade.getProduct(code));
	}

	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

}
