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
package com.clients.web.converters;

import de.hybris.platform.cms2lib.model.components.BannerComponentModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.BannerData;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.URLHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * The Class BannerConverter.
 */
public class BannerConverter implements Converter<BannerComponentModel, BannerData>
{

	/** The url helper. */
	protected URLHelper urlHelper;

	/** The page helper. */
	protected PageHelper pageHelper;

	/**
	 * Convert all.
	 * 
	 * @param sources
	 *           the sources
	 * 
	 * @return the collection< banner data>
	 */
	public Collection<BannerData> convertAll(final Collection<BannerComponentModel> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<BannerData> result = new ArrayList<BannerData>();
		for (final BannerComponentModel source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.dto.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public BannerData convert(final BannerComponentModel source) throws ConversionException
	{
		return convert(source, new BannerData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.dto.converter.Converter#convert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public BannerData convert(final BannerComponentModel source, final BannerData prototype) throws ConversionException
	{
		if (source.getMedia() != null)
		{
			prototype.setImageURL(source.getMedia().getURL());
		}
		prototype.setExternal(source.isExternal());
		prototype.setHeadline(source.getHeadline());
		prototype.setContent(source.getContent());
		if (!StringUtils.isBlank(source.getPageLabelOrId()) && pageHelper.getPageByLabelOrIdSafe(source.getPageLabelOrId()) != null)
		{
			prototype.setImageLink(urlHelper.getURLForContentPage(source.getPageLabelOrId()));
			prototype.setContentPage(true);
		}
		else
		{
			prototype.setImageLink(source.getUrlLink());
			prototype.setContentPage(false);
		}
		return prototype;
	}

	/**
	 * Sets the url helper.
	 * 
	 * @param urlHelper
	 *           the new url helper
	 */
	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

	/**
	 * Sets the page helper.
	 * 
	 * @param pageHelper
	 *           the new page helper
	 */
	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}

}
