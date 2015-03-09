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


public class PriceData implements Serializable
{
	private String currencyIso;
	private double value;
	private long quantity;
	private String formattedPrice;

	public String getCurrencyIso()
	{
		return currencyIso;
	}

	public void setCurrencyIso(final String currencyIso)
	{
		this.currencyIso = currencyIso;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(final double value)
	{
		this.value = value;
	}

	public long getQuantity()
	{
		return quantity;
	}

	public void setQuantity(final long quantity)
	{
		this.quantity = quantity;
	}

	public String getFormattedPrice()
	{
		return formattedPrice;
	}

	public void setFormattedPrice(final String formattedPrice)
	{
		this.formattedPrice = formattedPrice;
	}
}
