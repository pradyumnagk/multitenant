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
package com.clients.web.facades;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import com.clients.web.data.CategoryData;
import com.clients.web.data.PaginationData;
import com.clients.web.data.ProductData;
import com.clients.web.data.ProductResultData;

import java.util.Collection;
import java.util.List;


public interface ProductFacade
{
	ProductData getProduct(final ProductModel source, final List<String> categoryPath);

	ProductData getProduct(String catalogId, List<String> categoryPath, String productCode);

	ProductData getProduct(String productCode);

	List<ProductData> getProducts(String catalogId, Collection<String> productCodes);

	ProductData getProduct(String catalogId, String productCode);

	ProductResultData getProductsFromCategory(CategoryData category, PaginationData paginationData);

	ProductResultData getProductsFromCategory(String categoryCode, PaginationData paginationData);

	ProductResultData getProducts(List<ProductModel> products, PaginationData paginationData);

	ProductResultData getProductsFromCategory(CategoryModel category, PaginationData paginationData);

	ProductResultData getProductsFromCodes(List<String> codes, PaginationData paginationData);

	/**
	 * Tries to get a path to a product. Since a product can exist in more than one categorie, this method not always
	 * returns the expected result. When a product exist in more than one category, it always uses the first it finds.
	 * 
	 * @param product
	 *           Product for which the path should be generated
	 * @return the path to this product (catalogId/[list of categories]/productCode)
	 */
	List<String> getPathForProduct(ProductData product);

	List<ProductData> getPathForProducts(final List<ProductData> products);

}
