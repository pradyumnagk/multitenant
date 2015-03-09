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

import de.hybris.platform.cms2lib.enums.FlashQuality;
import de.hybris.platform.cms2lib.enums.FlashSalign;
import de.hybris.platform.cms2lib.enums.FlashScale;
import de.hybris.platform.cms2lib.enums.FlashWmode;
import com.clients.web.converters.FlashComponentConverter;
import de.hybris.platform.cms2lib.model.components.FlashComponentModel;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


/**
 * The Class FlashBannerComponentController.
 */
public class FlashComponentController extends AbstractCMSComponentController<FlashComponentModel>
{

	private FlashComponentConverter flashBannerConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.clients.web.controllers.components.cms2.AbstractCMSComponentController#doHandleRequest(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * de.hybris.platform.cms2.model.contents.components.SimpleCMSComponentModel)
	 */
	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final FlashComponentModel component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put("banner", flashBannerConverter.convert(component));
		model.put("params", getFlashParams(component));
		return new ModelAndView(view, model);
	}

	/**
	 * Sets the flash banner converter.
	 * 
	 * @param flashBannerConverter
	 *           the flashBannerConverter to set
	 */
	public void setFlashBannerConverter(final FlashComponentConverter flashBannerConverter)
	{
		this.flashBannerConverter = flashBannerConverter;
	}

	private String getFlashParams(final FlashComponentModel component)
	{
		final FlashScale scale = component.getScale();
		final FlashQuality quality = component.getQuality();
		final FlashSalign sAlign = component.getSAlign();
		final FlashWmode wmode = component.getWmode();
		final Boolean play = component.getPlay();
		final Boolean loop = component.getLoop();
		final Boolean menu = component.getMenu();
		final String bgcolor = component.getBgcolor();

		final StringBuilder params = new StringBuilder("{");
		if (play != null)
		{
			params.append("play: '").append(play.toString()).append("',");
		}
		if (loop != null)
		{
			params.append("loop: '").append(loop.toString()).append("',");
		}
		if (menu != null)
		{
			params.append("menu: '").append(menu.toString()).append("',");
		}
		if (wmode != null)
		{
			params.append("wmode: '").append(wmode.getCode()).append("',");
		}
		if (quality != null)
		{
			params.append("quality: '").append(quality.getCode()).append("',");
		}
		if (scale != null)
		{
			params.append("scale: '").append(scale.getCode()).append("',");
		}
		if (sAlign != null)
		{
			params.append("salign: '").append(sAlign.getCode()).append("',");
		}
		if (bgcolor != null)
		{
			params.append("bgcolor: '").append(bgcolor).append("',");
		}
		params.append("};");
		return params.toString();
	}
}
