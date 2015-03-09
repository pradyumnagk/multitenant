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

import org.apache.commons.lang.StringUtils;


public class ProductResultData implements Serializable
{

	private List<ProductData> products;
	private int page;
	private long pageCount;
	private CategoryData category;
	private String resultName;
	private int pageSize;
	private String orderKey;
	private Long numberOfResults;


	public List<ProductData> getProducts()
	{
		if (products == null)
		{
			return Collections.EMPTY_LIST;
		}
		return products;
	}

	public void setProducts(final List<ProductData> products)
	{
		this.products = products;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(final int page)
	{
		this.page = page;
	}

	public long getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(final long pageCount)
	{
		this.pageCount = pageCount;
	}

	public CategoryData getCategory()
	{
		return category;
	}

	public void setCategory(final CategoryData category)
	{
		this.category = category;
	}

	public String getResultName()
	{
		return resultName;
	}

	public void setResultName(final String resultName)
	{
		this.resultName = resultName;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(final int pageSize)
	{
		this.pageSize = pageSize;
	}

	public String getOrderKey()
	{
		return orderKey;
	}

	public void setOrderKey(final String orderKey)
	{
		this.orderKey = orderKey;
	}


	public Long getNumberOfResults()
	{
		return numberOfResults;
	}


	public void setNumberOfResults(final Long numberOfResults)
	{
		this.numberOfResults = numberOfResults;
	}

	public void setNumberOfResults(final long numberOfResults)
	{
		this.numberOfResults = Long.valueOf(numberOfResults);
	}

	public boolean isNumberOfResultsAvailable()
	{
		return this.numberOfResults != null;
	}

	public boolean isResultNameAvailable()
	{
		return !StringUtils.isEmpty(resultName);
	}

	public boolean isPageSizeAll()
	{
		return this.pageSize == Integer.MAX_VALUE;
	}


}
