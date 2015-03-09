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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class ProductData implements Serializable
{
	private String code;
	private String name;
	private String description;
	private String thumbnailURL;
	private String pictureURL;
	private String catalogId;
	private List<String> path;
	private Collection<PriceData> prices;
	private Collection<CustomerReviewData> reviews;
	private Double averageRating;

	public ProductData()
	{
		this.prices = new ArrayList<PriceData>();
		this.reviews = new ArrayList<CustomerReviewData>();
	}

	public boolean isDataSheetAvailable()
	{
		return false;
	}

	public boolean isFaqAvailable()
	{
		return false;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(final String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getPathAsString()
	{
		if (path != null)
		{
			return StringUtils.join(path.iterator(), '/');
		}
		else
		{
			return null;
		}
	}

	public List<String> getPath()
	{
		return path;
	}

	public void setPath(final List<String> path)
	{
		this.path = path;
	}

	public String getThumbnailURL()
	{
		return thumbnailURL;
	}

	public void setThumbnailURL(final String thumbnailURL)
	{
		this.thumbnailURL = thumbnailURL;
	}

	public String getPictureURL()
	{
		return pictureURL;
	}

	public void setPictureURL(final String pictureURL)
	{
		this.pictureURL = pictureURL;
	}

	public Collection<PriceData> getPrices()
	{
		return prices;
	}

	public void setPrices(final Collection<PriceData> prices)
	{
		this.prices = prices;
	}

	public Collection<CustomerReviewData> getReviews()
	{
		return reviews;
	}

	public void setReviews(final Collection<CustomerReviewData> reviews)
	{
		this.reviews = reviews;
	}

	public int getNumberOfReviews()
	{
		return this.reviews.size();
	}

	public boolean isReviewAvailable()
	{
		return !this.reviews.isEmpty();
	}

	public Double getAverageRating()
	{
		return (averageRating == null) ? new Double(0) : averageRating;
	}

	public void setAverageRating(final Double averageRating)
	{
		this.averageRating = averageRating;
	}

	public String getCatalogId()
	{
		return catalogId;
	}

	public void setCatalogId(final String catalogId)
	{
		this.catalogId = catalogId;
	}

	public long getAverageRatingAsInt()
	{
		return Math.round(getAverageRating().doubleValue());
	}

	/**
	 * @return If only one PriceData is available, returns this. If more than one is available, returns the cheapest one.
	 */
	public PriceData getPrice()
	{
		if (prices.size() == 1)
		{
			return prices.iterator().next();
		}
		double result = Double.MAX_VALUE;
		PriceData lowest = null;
		for (final PriceData price : prices)
		{
			if (price.getValue() < result)
			{
				result = price.getValue();
				lowest = price;
			}
		}
		return lowest;
	}

	public boolean isMoreThanOnePrice()
	{
		return this.prices.size() > 1;
	}



}
