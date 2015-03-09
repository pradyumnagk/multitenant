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


public class CartEntryData implements Serializable
{

	private int entryNumber;
	private long quantity;
	private ProductData product;
	private double basePrice;
	private String formattedBasePrice;
	private double totalPrice;
	private String formattedTotalPrice;

	public int getEntryNumber()
	{
		return entryNumber;
	}

	public void setEntryNumber(final int entryNumber)
	{
		this.entryNumber = entryNumber;
	}

	public long getQuantity()
	{
		return quantity;
	}

	public void setQuantity(final long quantity)
	{
		this.quantity = quantity;
	}

	public ProductData getProduct()
	{
		return product;
	}

	public void setProduct(final ProductData product)
	{
		this.product = product;
	}

	public double getBasePrice()
	{
		return basePrice;
	}

	public void setBasePrice(final double basePrice)
	{
		this.basePrice = basePrice;
	}

	public String getFormattedBasePrice()
	{
		return formattedBasePrice;
	}

	public void setFormattedBasePrice(final String formattedBasePrice)
	{
		this.formattedBasePrice = formattedBasePrice;
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
