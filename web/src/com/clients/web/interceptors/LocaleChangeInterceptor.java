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
package com.clients.web.interceptors;

import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.servicelayer.i18n.I18NService;
import com.clients.web.helpers.CatalogHelper;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;


public class LocaleChangeInterceptor extends AbstractInterceptor
{

	protected final static Logger LOG = Logger.getLogger(LocaleChangeInterceptor.class.getName());

	public final static String ISOCODE_PARA = "isoCode";

	protected I18NService i18nService;
	protected CatalogHelper catalogHelper;

	@Override
	public boolean doPreHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final String actionName) throws ServletException
	{
		//check if there is a (new) isocode parameter in the request 
		final String isoCode = request.getParameter(ISOCODE_PARA);
		if (!StringUtils.isEmpty(isoCode))
		{
			final Locale locale = getLocale(isoCode);
			if (locale != null)
			{
				i18nService.setCurrentLocale(locale);
			}
		}

		//set spring locale to the same locale as used in the hybris context
		final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		localeResolver.setLocale(request, response, i18nService.getCurrentLocale());
		return true;
	}

	/**
	 * Will return a locale based upon the given isocode. If the catalog does not support this language, it will return
	 * null
	 * 
	 * @param isoCode
	 *           ISO Language Code
	 * @return a new Locale or null if the catalog does not support this language
	 */
	protected Locale getLocale(final String isoCode)
	{
		if (catalogContainsLanguage(isoCode))
		{
			return new Locale(isoCode);
		}
		return null;
	}

	protected boolean catalogContainsLanguage(final String isoCode)
	{
		final CatalogVersionModel cv = catalogHelper.getCurrentCatalogVersion();
		if (cv == null)
		{
			return false;
		}
		final CatalogModel catalog = cv.getCatalog();
		for (final LanguageModel lang : catalog.getLanguages())
		{
			if (lang.getIsocode().equals(isoCode))
			{
				return true;
			}
		}
		LOG.warn("isoCode [" + isoCode + "] is not supported by the current catalog [" + catalog.getId()
				+ "]. Locale won't be switched.");
		return false;
	}

	public void setI18nService(final I18NService service)
	{
		i18nService = service;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}


}
