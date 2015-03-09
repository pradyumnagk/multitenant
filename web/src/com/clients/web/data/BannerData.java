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

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;


public class BannerData extends AbstractBannerData implements Serializable
{
	private String headline;
	private String content;

	public String getHeadline()
	{
		return headline;
	}

	public void setHeadline(final String headline)
	{
		this.headline = headline;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public boolean isHeadlineAvailable()
	{
		return !StringUtils.isEmpty(headline);
	}

	public boolean isContentAvailable()
	{
		return !StringUtils.isEmpty(content);
	}

}
