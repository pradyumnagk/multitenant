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

import de.hybris.platform.cms2lib.model.components.BannerComponentModel;
import com.clients.web.converters.BannerConverter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


public class BannerComponentController extends AbstractCMSComponentController<BannerComponentModel>
{

	private BannerConverter bannerConverter;

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final BannerComponentModel component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("banner", bannerConverter.convert(component));
		return new ModelAndView(view, model);
	}

	public void setBannerConverter(final BannerConverter bannerConverter)
	{
		this.bannerConverter = bannerConverter;
	}

}
