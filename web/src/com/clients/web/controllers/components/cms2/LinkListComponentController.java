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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;


public class LinkListComponentController extends AbstractCMSComponentController<LinkListComponentModel1>
{

	protected final static Logger LOG = Logger.getLogger(LinkListComponentController.class.getName());

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final LinkListComponentModel1 component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		final List<String> linkComponentNames = new ArrayList<String>();
		final List<CMSLinkComponentModel> links = component.getLinks();

		if (!links.isEmpty())
		{
			for (final CMSLinkComponentModel link : links)
			{
				linkComponentNames.add(link.getUid());
			}
		}

		int linksPerColumn = linkComponentNames.size();
		Integer columnNum = Integer.valueOf(1);
		if (component.getColumns().intValue() > 1)
		{
			columnNum = component.getColumns();
			linksPerColumn = (int) Math.ceil((double) linkComponentNames.size() / columnNum.intValue());
		}

		model.put("columns", columnNum);
		model.put("linksPerColumn", Integer.valueOf(linksPerColumn));
		model.put("links", linkComponentNames);

		return new ModelAndView(getView(), model);
	}
}
