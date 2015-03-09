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
package com.clients.web.decorators;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.util.UrlPathHelper;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.ConfigLoader;


public class ConfigDecoratorMapper extends com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper
{

	protected final static Logger LOG = Logger.getLogger(ConfigDecoratorMapper.class.getName());

	private ConfigLoader configLoader = null;
	private UrlPathHelper urlPathHelper = null;

	@Override
	public void init(final Config config, final Properties properties, final DecoratorMapper parent) throws InstantiationException
	{
		super.init(config, properties, parent);
		try
		{
			final String fileName = properties.getProperty("config", "/WEB-INF/decorators.xml");
			configLoader = new ConfigLoader(fileName, config);
			urlPathHelper = new UrlPathHelper();
		}
		catch (final Exception e)
		{
			throw new InstantiationException(e.toString());
		}
	}

	@Override
	public Decorator getDecorator(final HttpServletRequest request, final Page page)
	{
		String thisPath = urlPathHelper.getPathWithinApplication(request);

		// getServletPath() returns null unless the mapping corresponds to a servlet
		if (thisPath == null)
		{
			final String requestURI = request.getRequestURI();
			if (request.getPathInfo() != null)
			{
				// strip the pathInfo from the requestURI
				thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
			}
			else
			{
				thisPath = requestURI;
			}
		}
		else if ("".equals(thisPath))
		{
			// in servlet 2.4, if a request is mapped to '/*', getServletPath returns null (SIM-130)
			thisPath = request.getPathInfo();
		}

		String name = null;
		try
		{
			name = configLoader.getMappedName(thisPath);
		}
		catch (final ServletException e)
		{
			e.printStackTrace();
		}

		final Decorator result = getNamedDecorator(request, name);
		return result == null ? super.getDecorator(request, page) : result;
	}

}
