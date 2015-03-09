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

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.contents.components.SimpleCMSComponentModel;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public abstract class AbstractCMSComponentController<T extends SimpleCMSComponentModel> implements Controller
{
	protected final static Logger LOG = Logger.getLogger(AbstractCMSComponentController.class.getName());

	private final static String COMPONENT_UID = "componentUid";

	protected String errorView;

	protected String view;
	protected Map<String, String> views;

	protected CMSComponentService cmsComponentService;

	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		String componentUid = (String) request.getAttribute(COMPONENT_UID);
		if (StringUtils.isEmpty(componentUid))
		{
			componentUid = request.getParameter(COMPONENT_UID);
		}
		if (StringUtils.isEmpty(componentUid))
		{
			return new ModelAndView(errorView, Collections.singletonMap("error", "No component id found"));
		}
		try
		{
			/*
			 * See PLA-9785 + PLA-9794 Do not remove the (unnecessary) cast "(T)" until the java bug is fixed.
			 */
			final T component = (T) cmsComponentService.getSimpleCMSComponent(componentUid);
			if (component == null)
			{
				return new ModelAndView(errorView, Collections.singletonMap("error", "Item witd id [" + componentUid + "] is null"));
			}
			final ModelAndView result = doHandleRequest(request, response, component);
			result.getModel().put("componentID", component.getUid());
			return result;
		}
		catch (final CMSItemNotFoundException nfe)
		{
			return new ModelAndView(errorView, Collections.singletonMap("error", "Component witd id [" + componentUid
					+ "] not found: " + nfe.getMessage()));
		}
	}

	public abstract ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response, T component)
			throws Exception;

	public void setErrorView(final String errorView)
	{
		this.errorView = errorView;
	}

	public void setCmsComponentService(final CMSComponentService cmsComponentService)
	{
		this.cmsComponentService = cmsComponentService;
	}

	protected String getView()
	{
		return view;
	}

	public void setView(final String view)
	{
		this.view = view;
	}

	protected Map<String, String> getViews()
	{
		return views;
	}

	public void setViews(final Map<String, String> views)
	{
		this.views = views;
	}

}
