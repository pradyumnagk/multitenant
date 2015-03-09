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
import java.util.Collections;
import java.util.List;


public class CartData implements Serializable
{
	private List<CartEntryData> entries;
	private double totalPrice;
	private String formattedTotalPrice;

	public CartEntryData getEntry(final int entryNumber)
	{
		for (final CartEntryData entry : getEntries())
		{
			if (entry.getEntryNumber() == entryNumber)
			{
				return entry;
			}
		}
		return null;
	}

	public List<CartEntryData> getEntries()
	{
		if (entries == null)
		{
			return Collections.EMPTY_LIST;
		}
		return entries;
	}

	public long getTotalQuantity()
	{
		long qty = 0;
		for (final CartEntryData entry : getEntries())
		{
			qty += entry.getQuantity();
		}
		return qty;
	}

	public void setEntries(final List<CartEntryData> entries)
	{
		this.entries = entries;
	}

	public double getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(final double totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public String getFormattedTotalPrice()
	{
		return formattedTotalPrice;
	}

	public void setFormattedTotalPrice(final String formattedTotalPrice)
	{
		this.formattedTotalPrice = formattedTotalPrice;
	}
}
