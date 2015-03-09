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

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;


public class CartAndWishlistComponentController extends AbstractCMSComponentController<CartAndWishlistComponentModel1>
{
	protected final static Logger LOG = Logger.getLogger(CartAndWishlistComponentController.class.getName());

	protected final static String CART = "cart";
	protected final static String WISHLIST = "wishlist";

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final CartAndWishlistComponentModel1 component) throws Exception
	{
		final String mode = component.getMode().getCode();
		Map<String, Object> model = null;
		if (CART.equals(mode))
		{
			LOG.debug("Showing cart");
			model = prepareCartView();
		}
		else
		{
			LOG.debug("Showing wishlists");
			model = prepareWishlistView();
		}
		model.put("backURL", getBackURL(request));
		final String view = getViews().get(mode);
		return new ModelAndView(view, model);
	}

	protected String getBackURL(final HttpServletRequest request)
	{
		final String contextPath = request.getContextPath();
		final String currentURL = (String) request.getAttribute("currentURL");
		String from = request.getParameter("from");
		if (from == null || from.equals(contextPath + currentURL))
		{
			from = contextPath;
		}
		return from;
	}

	protected Map<String, Object> prepareCartView()
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		return model;
	}

	protected Map<String, Object> prepareWishlistView()
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		return model;
	}

}
