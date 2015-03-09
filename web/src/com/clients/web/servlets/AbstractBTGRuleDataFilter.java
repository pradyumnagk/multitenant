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
package com.clients.web.servlets;

import de.hybris.platform.btg.events.AbstractBTGRuleDataEvent;
import de.hybris.platform.cms2.misc.CMSFilter;
import com.clients.web.controllers.pages.PageSourceObjectProvider;
import com.clients.web.controllers.pages.ResourcePathProvider;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.RequestHelper;
import com.clients.web.servlets.util.FilterSpringUtil;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Base class for BTG Rule Data Filters. BTG Rule Data Filters collect informations from request and make them available
 * to BTG framework by publishing {@link AbstractBTGRuleDataEvent} (actually, it will always be some concrete subclass
 * of AbstractBTGRuleDataEvent).
 */
public abstract class AbstractBTGRuleDataFilter<BO> extends OncePerRequestFilter implements CMSFilter
{
	protected static final String PAGE_SOURCE_OBJECT_PROVIDER_BEAN_NAME_PARAM = "PageSourceObjectProviderBean";
	protected static final String RESOURCE_PATH_PROVIDER_BEAN_NAME_PARAM = "ResourcePathProviderBean";

	protected BO getBusinessObject(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException
	{
		final ResourcePathProvider resourcePathProvider = this.getResourcePathProvider(request);
		final PageSourceObjectProvider<BO> pageSourceObjectProvider = this.getPageSourceObjectProvider(request);
		try
		{
			return pageSourceObjectProvider.getPageSourceObject(resourcePathProvider.getResourcePath(request), request, response);
		}
		catch (final Exception ex)
		{
			throw new ServletException(ex);
		}
	}

	/**
	 * Publishes a single BTG Rule Data Event.
	 */
	protected <T extends Serializable> void publishBTGRuleDataEvent(final AbstractBTGRuleDataEvent<T> btgRuleDataEvent,
			final HttpServletRequest httpServletRequest)
	{
		RequestScopedBTGRuleDataServletRequestListener.publishEvent(btgRuleDataEvent, httpServletRequest);
	}

	/**
	 * Publishes a single BTG Rule Data Event and automatically registers a corresponding data removing event. The
	 * dataInsertingEvent parameter must be a data inserting event. This method ensures that corresponding data removing
	 * event will be published automatically at the end of request processing. This means data will be removed at the end
	 * of request processing.
	 */
	protected <T extends Serializable> void publishRequestScopedBTGRuleDataInsertingEvent(
			final AbstractBTGRuleDataEvent<T> dataInsertingEvent, final HttpServletRequest httpServletRequest)
	{
		RequestScopedBTGRuleDataServletRequestListener.publishRequestScopedDataInsertingEvent(dataInsertingEvent,
				httpServletRequest);
	}

	protected RequestHelper getRequestHelper(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "requestHelper", RequestHelper.class);
	}

	protected CatalogHelper getCatalogHelper(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "catalogHelper", CatalogHelper.class);
	}


	protected PageSourceObjectProvider<BO> getPageSourceObjectProvider(final HttpServletRequest httpServletRequest)
	{
		final String providerBeanName = this.getFilterConfig().getInitParameter(PAGE_SOURCE_OBJECT_PROVIDER_BEAN_NAME_PARAM);
		return FilterSpringUtil.getSpringBean(httpServletRequest, providerBeanName, PageSourceObjectProvider.class);
	}

	protected ResourcePathProvider getResourcePathProvider(final HttpServletRequest httpServletRequest)
	{
		final String providerBeanName = this.getFilterConfig().getInitParameter(RESOURCE_PATH_PROVIDER_BEAN_NAME_PARAM);
		return FilterSpringUtil.getSpringBean(httpServletRequest, providerBeanName, ResourcePathProvider.class);
	}
}
