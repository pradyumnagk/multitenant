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
package com.clients.web.data;

import org.apache.commons.lang.StringUtils;


public abstract class AbstractBannerData
{
	protected String imageURL;
	protected String imageLink;
	protected boolean external;
	protected boolean contentPage;
	protected String mime;

	public boolean isValid()
	{
		return !StringUtils.isEmpty(this.imageURL);
	}

	public boolean isValidLink()
	{
		return !StringUtils.isEmpty(this.imageLink);
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * @param imageURL
	 *           the imageURL to set
	 */
	public void setImageURL(final String imageURL)
	{
		this.imageURL = imageURL;
	}

	/**
	 * @return the imageLink
	 */
	public String getImageLink()
	{
		return imageLink;
	}

	/**
	 * @param imageLink
	 *           the imageLink to set
	 */
	public void setImageLink(final String imageLink)
	{
		this.imageLink = imageLink;
	}

	/**
	 * @return the external
	 */
	public boolean isExternal()
	{
		return external;
	}

	/**
	 * @param external
	 *           the external to set
	 */
	public void setExternal(final boolean external)
	{
		this.external = external;
	}

	/**
	 * @return the contentPage
	 */
	public boolean isContentPage()
	{
		return contentPage;
	}

	/**
	 * @param contentPage
	 *           the contentPage to set
	 */
	public void setContentPage(final boolean contentPage)
	{
		this.contentPage = contentPage;
	}

	/**
	 * @return the mime
	 */
	public String getMime()
	{
		return mime;
	}

	/**
	 * @param mime
	 *           the mime to set
	 */
	public void setMime(final String mime)
	{
		this.mime = mime;
	}
}
