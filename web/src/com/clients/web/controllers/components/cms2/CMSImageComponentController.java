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

import de.hybris.platform.cms2.model.contents.components.CMSImageComponentModel;
import de.hybris.platform.core.model.media.MediaModel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;


public class CMSImageComponentController extends AbstractCMSComponentController<CMSImageComponentModel>
{

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final CMSImageComponentModel component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		final MediaModel media = component.getMedia();
		if (media != null)
		{
			final String url = media.getURL();
			final String name = media.getRealfilename();
			if (!StringUtils.isEmpty(url))
			{
				model.put("url", url);
				model.put("name", name);
			}
		}
		return new ModelAndView(getView(), model);
	}

}
