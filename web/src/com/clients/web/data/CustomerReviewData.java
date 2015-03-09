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

import de.hybris.platform.core.model.user.UserModel;

import java.io.Serializable;
import java.util.Date;


public class CustomerReviewData implements Serializable
{

	private String headline;
	private String comment;
	private long rating;
	private Date date;
	private UserModel user;

	public String getHeadline()
	{
		return headline;
	}

	public void setHeadline(final String headline)
	{
		this.headline = headline;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(final String comment)
	{
		this.comment = comment;
	}

	public long getRating()
	{
		return rating;
	}

	public void setRating(final long rating)
	{
		this.rating = rating;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(final Date date)
	{
		this.date = date;
	}

	public UserModel getUser()
	{
		return user;
	}

	public void setUser(final UserModel user)
	{
		this.user = user;
	}


}
