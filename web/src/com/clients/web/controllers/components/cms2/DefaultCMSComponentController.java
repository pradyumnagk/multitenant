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

import de.hybris.platform.cms2.model.contents.components.SimpleCMSComponentModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;


public class DefaultCMSComponentController extends AbstractCMSComponentController<SimpleCMSComponentModel>
{

	protected String jspBaseDir;
	protected String fileExtension;

	private ModelService modelService;

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final SimpleCMSComponentModel component) throws Exception
	{
		final String typeCode = component.getTypeCode();
		final Map<String, Object> model = new HashMap<String, Object>();
		final Collection<String> properties = cmsComponentService.getEditorProperties(component);
		for (final String property : properties)
		{
			final Object value = getModelService().getAttributeValue(component, property);
			model.put(property, value);
		}
		final String view = getViewName(typeCode);
		LOG.debug("using view " + view);
		return new ModelAndView(view, model);
	}

	protected String getViewName(final String typeCode)
	{
		return jspBaseDir + StringUtils.uncapitalize(typeCode) + fileExtension;
	}

	public void setJspBaseDir(final String jspBaseDir)
	{
		this.jspBaseDir = jspBaseDir;
	}

	public void setFileExtension(final String fileExtension)
	{
		this.fileExtension = fileExtension;
	}

	public ModelService getModelService()
	{
		return modelService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}
}
