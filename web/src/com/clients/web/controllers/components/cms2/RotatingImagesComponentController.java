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


import de.hybris.platform.cms2lib.model.components.RotatingImagesComponentModel;
import com.clients.web.converters.BannerConverter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


public class RotatingImagesComponentController extends AbstractCMSComponentController<RotatingImagesComponentModel>
{

	protected BannerConverter bannerConverter;

	/**
	 * Default value for time out between image change.
	 */
	public final static Integer TIMEOUT_DEFAULT = Integer.valueOf(5);

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final RotatingImagesComponentModel component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();

		//get and set the timeout (rotating cyclus)
		final Integer timeout = Integer.valueOf(component.getTimeout().intValue() * 1000);
		model.put("timeout", (timeout != null && timeout.intValue() > 0) ? timeout : TIMEOUT_DEFAULT);

		// get and set the effect
		model.put("effect", component.getEffect().getCode());

		//put the banner items in the model
		model.put("banners", bannerConverter.convertAll(component.getBanners()));

		return new ModelAndView(getView(), model);
	}

	public void setBannerConverter(final BannerConverter bannerConverter)
	{
		this.bannerConverter = bannerConverter;
	}

}
