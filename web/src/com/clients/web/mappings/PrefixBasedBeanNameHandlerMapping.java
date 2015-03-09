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
package com.clients.web.mappings;

import com.clients.web.helpers.RequestHelper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;


public class PrefixBasedBeanNameHandlerMapping extends AbstractHandlerMapping implements BeanFactoryAware
{

	protected BeanFactory beanFactory;
	protected RequestHelper requestHelper;

	@Override
	protected Object getHandlerInternal(final HttpServletRequest request) throws Exception
	{
		final String beanName = requestHelper.getActionName(request);
		if (beanFactory.containsBean(beanName))
		{
			return beanFactory.getBean(beanName);
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException
	{
		this.beanFactory = beanFactory;
	}

	public void setRequestHelper(final RequestHelper requestHelper)
	{
		this.requestHelper = requestHelper;
	}


}
