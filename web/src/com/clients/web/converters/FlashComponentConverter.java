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

import de.hybris.platform.cms2lib.model.components.FlashComponentModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.FlashBannerData;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.URLHelper;

import org.apache.commons.lang.StringUtils;


/**
 * The Class FlashBannerConverter. Sets up all needed values for view
 */
public class FlashComponentConverter implements Converter<FlashComponentModel, FlashBannerData>
{
	protected URLHelper urlHelper;
	protected PageHelper pageHelper;
	private static final String FLASH_ELEMENT_BASE_ID = "flashContent";
	private static final int DEFAULT_WIDTH = 557;
	private static final int DEFAULT_HEIGHT = 150;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.dto.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public FlashBannerData convert(final FlashComponentModel source) throws ConversionException
	{
		return convert(source, new FlashBannerData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.dto.converter.Converter#convert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public FlashBannerData convert(final FlashComponentModel source, final FlashBannerData prototype) throws ConversionException
	{
		final Integer width = source.getWidth();
		final Integer height = source.getHeight();
		prototype.setFlashElementId(generateFlashId(source));
		prototype.setWidth(width == null ? DEFAULT_WIDTH : width.intValue());
		prototype.setHeight(height == null ? DEFAULT_HEIGHT : height.intValue());

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

		if (source.getMedia() != null)
		{
			prototype.setImageURL(source.getMedia().getURL());
		}
		prototype.setExternal(source.isExternal());
		return prototype;
	}

	/**
	 * Generate unique id for html element which will hold Flash animation file.
	 * 
	 * @param source
	 *           the component model
	 * 
	 * @return the string
	 */
	private String generateFlashId(final FlashComponentModel source)
	{
		return FLASH_ELEMENT_BASE_ID + source.getUid();
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
