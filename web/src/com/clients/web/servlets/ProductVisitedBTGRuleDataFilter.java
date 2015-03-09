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
package com.clients.web.servlets;

import de.hybris.platform.btg.events.ProductVisitedBTGRuleDataEvent;
import de.hybris.platform.core.model.product.ProductModel;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This filter handles "User has visited a Product" use case. Based on the http request URL it determines a Product that
 * has been requested by web user, gets the Product PK and publishes a {@link ProductVisitedBTGRuleDataEvent} that
 * encapsulates the PK.
 */
public class ProductVisitedBTGRuleDataFilter extends AbstractBTGRuleDataFilter<ProductModel>
{

	@Override
	protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException
	{

		final ProductModel productModel = getBusinessObject(httpServletRequest, response);

		if (productModel != null)
		{
			final String productInfo = productModel.getPk().toString();
			final ProductVisitedBTGRuleDataEvent dataInsertingEvent = new ProductVisitedBTGRuleDataEvent(productInfo);
			this.publishBTGRuleDataEvent(dataInsertingEvent, httpServletRequest);
		}

		filterChain.doFilter(httpServletRequest, response);
	}
}
