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

import java.util.Date;


public class WishlistEntryData
{

	private Date addedDate;
	private String comment;
	private int desired;
	private int received;
	private String priority;
	private ProductData product;

	public Date getAddedDate()
	{
		return addedDate;
	}

	public void setAddedDate(final Date addedDate)
	{
		this.addedDate = addedDate;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(final String comment)
	{
		this.comment = comment;
	}

	public int getDesired()
	{
		return desired;
	}

	public void setDesired(final int desired)
	{
		this.desired = desired;
	}

	public int getReceived()
	{
		return received;
	}

	public void setReceived(final int received)
	{
		this.received = received;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(final String priority)
	{
		this.priority = priority;
	}

	public ProductData getProduct()
	{
		return product;
	}

	public void setProduct(final ProductData product)
	{
		this.product = product;
	}




}
