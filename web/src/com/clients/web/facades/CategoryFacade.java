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
import com.clients.web.data.CategoryData;

import java.util.List;


public interface CategoryFacade
{
	CategoryData getCategory(final String catalogId, final List<String> categoryPath);

	CategoryData getCategory(final String catalogId, final List<String> superCategoryPath, String categoryCode);

	List<CategoryData> getRootCategories();

	List<CategoryData> getSubCategories(CategoryData parentCategory);

	boolean hasSubCategories(CategoryData category);

	CategoryData getParentCategory(CategoryData category);

	CategoryData getCategory(CategoryModel category);

	List<String> getPathForCategory(CategoryData category);

	CategoryData getCategory(String code);

	CategoryData getCategory(CategoryModel category, List<String> categoryPath);

}
