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
package com.clients.web.converters;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.product.PriceService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.ProductData;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


public class ProductConverter implements Converter<ProductModel, ProductData>
{

	protected PriceService priceService;
	protected PriceInformationConverter priceInformationConverter;
	protected CustomerReviewService customerReviewService;
	protected CustomerReviewConverter customerReviewConverter;

	public List<ProductData> convertAll(final Collection<ProductModel> sources)
	{
		if (sources == null || sources.isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<ProductData> result = new ArrayList<ProductData>(sources.size());
		for (final ProductModel source : sources)
		{
			result.add(convert(source));
		}
		return result;
	}

	@Override
	public ProductData convert(final ProductModel source) throws ConversionException
	{
		final ProductData data = new ProductData();
		return convert(source, data);
	}

	@Override
	public ProductData convert(final ProductModel source, final ProductData prototype)
	{
		prototype.setCode(source.getCode());
		prototype.setDescription(source.getDescription());
		prototype.setName(source.getName());
		prototype.setCatalogId(source.getCatalogVersion().getCatalog().getId());
		if (source.getThumbnail() != null)
		{
			prototype.setThumbnailURL(source.getThumbnail().getURL());
		}
		if (source.getPicture() != null)
		{
			prototype.setPictureURL(source.getPicture().getURL());
		}


		final List<PriceInformation> prices = priceService.getPriceInformationsForProduct(source);
		if (CollectionUtils.isEmpty(prices))
		{
			if (CollectionUtils.isNotEmpty(source.getVariants()))
			{
				for (final VariantProductModel variantModel : source.getVariants())
				{
					prices.addAll(priceService.getPriceInformationsForProduct(variantModel));
				}
			}
			Collections.sort(prices, new Comparator<PriceInformation>()
			{
				@Override
				public int compare(final PriceInformation leftSide, final PriceInformation rightSide)
				{
					if (leftSide == null)
					{
						if (rightSide == null)
						{
							return 0;
						}
						else
						{
							return 1;
						}
					}
					else if (rightSide == null)
					{
						return -1;
					}
					return Double.valueOf(leftSide.getPriceValue().getValue()).compareTo(
							Double.valueOf(rightSide.getPriceValue().getValue()));
				}
			});
		}

		prototype.setPrices(priceInformationConverter.convertAll(prices));
		prototype.setReviews(customerReviewConverter.convertAll(customerReviewService.getAllReviews(source)));
		prototype.setAverageRating(customerReviewService.getAverageRating(source));
		return prototype;
	}

	public void setPriceService(final PriceService priceService)
	{
		this.priceService = priceService;
	}

	public void setPriceInformationConverter(final PriceInformationConverter priceInformationConverter)
	{
		this.priceInformationConverter = priceInformationConverter;
	}

	public void setCustomerReviewService(final CustomerReviewService customerReviewService)
	{
		this.customerReviewService = customerReviewService;
	}

	public void setCustomerReviewConverter(final CustomerReviewConverter customerReviewConverter)
	{
		this.customerReviewConverter = customerReviewConverter;
	}
}
