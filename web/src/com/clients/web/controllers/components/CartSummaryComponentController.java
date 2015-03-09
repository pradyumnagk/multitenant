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

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.clients.web.controllers.components.cms2.AbstractCMSComponentController;


public class CartSummaryComponentController extends AbstractCMSComponentController<CartSummaryComponentModel1>
{
	protected final static Logger LOG = Logger.getLogger(CartSummaryComponentController.class.getName());

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final CartSummaryComponentModel1 component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("cartSummary", component);

		return new ModelAndView(getView(), model);
	}
}
