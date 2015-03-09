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
package com.clients.web.controllers.forms.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;


public class CartItemInfo implements Serializable
{

	private String quantity;
	private String productCode;
	private String id;
	private String currentURL;
	private boolean quickAdd;
	private List<String> quantities;
	private String plusQuantityEntry;
	private String minusQuantityEntry;
	private String backURL;

	public CartItemInfo()
	{
		this.quantities = LazyList.decorate(new ArrayList<String>(), new Factory()
		{
			public Object create()
			{
				return "";
			}
		});
	}

	public void setQuantities(final List<String> quantities)
	{
		this.quantities = quantities;
	}

	public List<String> getQuantities()
	{
		return quantities;
	}

	public void setQuantity(final String quantity)
	{
		this.quantity = quantity;
	}

	public String getQuantity()
	{
		return quantity;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(final String productCode)
	{
		this.productCode = productCode;
	}

	public String getId()
	{
		return id;
	}

	public void setId(final String id)
	{
		this.id = id;
	}

	public boolean isQuickAdd()
	{
		return quickAdd;
	}

	public void setQuickAdd(final boolean quickAdd)
	{
		this.quickAdd = quickAdd;
	}

	public String getCurrentURL()
	{
		return currentURL;
	}

	public void setCurrentURL(final String currentURL)
	{
		this.currentURL = currentURL;
	}

	public String getPlusQuantityEntry()
	{
		return plusQuantityEntry;
	}

	public void setPlusQuantityEntry(final String plusQuantityEntry)
	{
		this.plusQuantityEntry = plusQuantityEntry;
	}

	public String getMinusQuantityEntry()
	{
		return minusQuantityEntry;
	}

	public void setMinusQuantityEntry(final String minusQuantityEntry)
	{
		this.minusQuantityEntry = minusQuantityEntry;
	}

	public void setBackURL(final String backURL)
	{
		this.backURL = backURL;
	}

	public String getBackURL()
	{
		return backURL;
	}


}
