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


/**
 * The Class FlashBannerData.
 */
public class FlashBannerData extends AbstractBannerData implements Serializable
{
	private String flashElementId;
	private int height;
	private int width;

	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Sets the height.
	 * 
	 * @param height
	 *           the height to set
	 */
	public void setHeight(final int height)
	{
		this.height = height;
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Sets the width.
	 * 
	 * @param width
	 *           the width to set
	 */
	public void setWidth(final int width)
	{
		this.width = width;
	}

	/**
	 * Gets the flash element id.
	 * 
	 * @return the flashElementId
	 */
	public String getFlashElementId()
	{
		return flashElementId;
	}

	/**
	 * Sets the flash element id.
	 * 
	 * @param flashElementId
	 *           the flashElementId to set
	 */
	public void setFlashElementId(final String flashElementId)
	{
		this.flashElementId = flashElementId;
	}
}
