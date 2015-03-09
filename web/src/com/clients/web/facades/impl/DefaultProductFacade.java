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
package com.clients.web.facades.impl;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.filter.ProductFilter;
import de.hybris.platform.classification.filter.ProductFilterResult;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import com.clients.web.converters.ProductConverter;
import com.clients.web.data.CategoryData;
import com.clients.web.data.PaginationData;
import com.clients.web.data.ProductData;
import com.clients.web.data.ProductResultData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.facades.ProductFacade;
import com.clients.web.helpers.ProductHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class DefaultProductFacade implements ProductFacade
{
	protected final static Logger LOG = Logger.getLogger(DefaultProductFacade.class.getName());

	protected ClassificationService classificationService;
	protected ProductService productService;
	protected ProductConverter productConverter;
	protected CatalogService catalogService;
	protected CategoryService categoryService;
	protected CategoryFacade categoryFacade;
	protected ProductHelper productHelper;

	protected CatalogVersionModel getCatalogVersion(final String catalogId) throws UnknownIdentifierException
	{
		return catalogService.getSessionCatalogVersion(catalogId);
	}

	@Override
	public ProductResultData getProductsFromCategory(final String categoryCode, final PaginationData paginationData)
	{
		try
		{
			final CategoryData categoryData = categoryFacade.getCategory(categoryCode);
			if (categoryData != null)
			{
				return getProductsFromCategory(categoryData, paginationData);
			}
		}
		catch (final SystemException e)
		{

			LOG.debug("Cannot retrive category by given code: " + categoryCode, e);
		}
		return null;
	}

	@Override
	public ProductResultData getProductsFromCategory(final CategoryData category, final PaginationData paginationData)
	{

		final int page = paginationData.getPageNumber();
		final int pageSize = paginationData.getPageSize();
		final CatalogVersionModel version = getCatalogVersion(category.getCatalogId());
		final CategoryModel source = categoryService.getCategory(version, category.getCode());
		final ProductFilter filter = new ProductFilter(source);
		if (page > 0)
		{
			filter.setCount(pageSize);
			filter.setStart((page - 1) * pageSize);
		}
		final ProductFilterResult result = classificationService.getProductsByFilter(filter);
		final ProductResultData resultData = new ProductResultData();
		final List<ProductData> products = productConverter.convertAll(result.getProducts());
		setPathForProducts(category, products);
		resultData.setProducts(products);
		resultData.setPage(page);
		int pageCount = result.getTotalCount() / pageSize;
		if (result.getTotalCount() % pageSize > 0)
		{
			pageCount++;
		}
		resultData.setPageCount(pageCount);
		resultData.setCategory(category);
		resultData.setOrderKey(paginationData.getOrderKey());
		resultData.setPageSize(pageSize);
		resultData.setResultName(category.getName());
		return resultData;
	}

	private void setPathForProducts(final CategoryData category, final List<ProductData> products)
	{
		for (final ProductData product : products)
		{
			final List<String> path = new ArrayList<String>(category.getPath());
			path.add(product.getCode());
			product.setPath(path);
		}
	}

	private List<ProductData> getProducts(final List<String> codes)
	{
		final List<ProductData> result = new ArrayList<ProductData>();
		ProductData p = null;
		for (final String code : codes)
		{
			try
			{
				String productCode = code;
				if (productCode.contains("/"))
				{
					productCode = StringUtils.substringAfterLast(productCode, "/");
				}
				final ProductModel product = productService.getProduct(productCode);
				p = productConverter.convert(product);
				p.setCatalogId(product.getCatalogVersion().getCatalog().getId());
				p.setPath(getPathForProduct(p));
				result.add(p);
			}
			catch (final Exception e)
			{
				LOG.debug("Could not get product with code <" + code + ">: " + e.getMessage(), e);
			}

		}

		return result;
	}

	@Override
	public ProductResultData getProductsFromCodes(final List<String> codes, final PaginationData paginationData)
	{
		final int page = paginationData.getPageNumber();
		final int pageSize = paginationData.getPageSize();
		final ProductResultData data = new ProductResultData();
		final List<ProductData> productDatas = getProducts(codes);
		final int size = productDatas.size();
		if (pageSize >= size)
		{
			data.setProducts(getPathForProducts(productDatas));
			return data;
		}
		final int pageCount = size / pageSize + ((size % pageSize != 0) ? 1 : 0);
		int start = (page - 1) * pageSize;
		if (start >= size)
		{
			start = size;
		}
		int end = (page * pageSize);
		if (end > size)
		{
			end = size;
		}
		final List<ProductData> result = productDatas.subList(start, end);
		data.setPage(page);
		data.setPageCount(pageCount);
		data.setProducts(getPathForProducts(result));
		data.setOrderKey(paginationData.getOrderKey());
		data.setPageSize(pageSize);
		return data;
	}

	@Override
	public ProductData getProduct(final ProductModel source, final List<String> categoryPath)
	{
		ProductData product = null;
		if (source != null)
		{
			product = productConverter.convert(source);
			final List<String> path = new ArrayList<String>(categoryPath);
			path.add(product.getCode());
			product.setPath(path);
		}
		return product;
	}

	@Override
	public ProductData getProduct(final String catalogId, final List<String> categoryPath, final String productCode)
	{
		final ProductModel source = productHelper.getProduct(catalogId, productCode);
		return getProduct(source, categoryPath);
	}

	@Override
	public ProductData getProduct(final String catalogId, final String productCode)
	{
		final ProductModel p = productHelper.getProduct(catalogId, productCode);
		final ProductData product = productConverter.convert(p);
		product.setPath(getPathForProduct(product));
		return product;
	}

	@Override
	public List<ProductData> getProducts(final String catalogId, final Collection<String> productCodes)
	{
		final List<ProductData> result = new ArrayList<ProductData>();
		for (final String code : productCodes)
		{
			String productCode = code;
			if (productCode.contains("/"))
			{
				productCode = StringUtils.substringAfterLast(productCode, "/");
			}
			final ProductModel p = productService.getProduct(getCatalogVersion(catalogId), productCode);
			final ProductData product = productConverter.convert(p);
			product.setPath(getPathForProduct(product));
			result.add(product);
		}
		return result;
	}

	@Override
	public List<String> getPathForProduct(final ProductData product)
	{
		final List<String> result = new ArrayList<String>();
		final ProductModel source = productService.getProduct(getCatalogVersion(product.getCatalogId()), product.getCode());
		Collection<CategoryModel> parents = source.getSupercategories();
		final String catalogId = product.getCatalogId();
		if (parents == null || parents.isEmpty())
		{
			LOG.debug("Product [" + product.getCode() + "] seems to be in no category");
			result.add(product.getCode());
			return result;
		}
		while (!parents.isEmpty())
		{
			parents = filterParents(parents, catalogId);
			if (parents.isEmpty())
			{
				LOG.debug("No category found which is in the same catalog as the product. Skipping here!");
				result.add(product.getCode());
				return result;
			}
			if (LOG.isDebugEnabled() && parents.size() > 1)
			{
				final List<String> d = new ArrayList<String>();
				for (final CategoryModel c : parents)
				{
					d.add(c.getName());
				}
				LOG
						.debug("Product ["
								+ product.getCode()
								+ "] is in more than one category. Taking the first one. This may not be the expected result! Found supercategories: ["
								+ StringUtils.join(d, "; ") + "]");

			}
			final CategoryModel cat = parents.iterator().next();
			result.add(0, cat.getCode());
			parents = cat.getSupercategories();
		}
		result.add(product.getCode());
		return result;
	}

	@Override
	public List<ProductData> getPathForProducts(final List<ProductData> products)
	{
		for (final ProductData product : products)
		{
			product.setPath(getPathForProduct(product));
		}
		return products;
	}

	@Override
	public ProductData getProduct(final String code)
	{
		try
		{
			String productCode = code;
			if (productCode.contains("/"))
			{
				productCode = StringUtils.substringAfterLast(productCode, "/");
			}
			final ProductModel model = productService.getProduct(productCode);
			final ProductData p = productConverter.convert(model);
			p.setCatalogId(model.getCatalogVersion().getCatalog().getId());
			p.setPath(getPathForProduct(p));
			return p;
		}
		catch (final Exception e)
		{
			LOG.debug("Could not get product with code <" + code + ">: " + e.getMessage(), e);
		}
		return null;
	}

	@Override
	public ProductResultData getProducts(final List<ProductModel> products, final PaginationData paginationData)
	{
		final int page = paginationData.getPageNumber();
		final int pageSize = paginationData.getPageSize();
		final ProductResultData data = new ProductResultData();
		final List<ProductData> productDatas = productConverter.convertAll(products);
		final int size = productDatas.size();
		if (pageSize >= size)
		{
			data.setProducts(getPathForProducts(productDatas));
			return data;
		}
		final int pageCount = size / pageSize + ((size % pageSize != 0) ? 1 : 0);
		int start = (page - 1) * pageSize;
		if (start >= size)
		{
			start = size;
		}
		int end = (page * pageSize);
		if (end > size)
		{
			end = size;
		}
		final List<ProductData> result = productDatas.subList(start, end);
		data.setPage(page);
		data.setPageCount(pageCount);
		data.setProducts(getPathForProducts(result));
		data.setOrderKey(paginationData.getOrderKey());
		data.setPageSize(pageSize);
		return data;
	}

	@Override
	public ProductResultData getProductsFromCategory(final CategoryModel category, final PaginationData paginationData)
	{
		final CategoryData data = categoryFacade.getCategory(category);
		return getProductsFromCategory(data, paginationData);
	}


	private Collection<CategoryModel> filterParents(final Collection<CategoryModel> parents, final String catalogId)
	{
		final List<CategoryModel> result = new ArrayList<CategoryModel>();
		for (final CategoryModel cat : parents)
		{
			if (cat.getCatalogVersion().getCatalog().getId().equals(catalogId))
			{
				result.add(cat);
			}
		}
		return result;
	}

	public void setProductConverter(final ProductConverter productConverter)
	{
		this.productConverter = productConverter;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	public void setCatalogService(final CatalogService catalogService)
	{
		this.catalogService = catalogService;
	}

	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	public void setCategoryFacade(final CategoryFacade categoryFacade)
	{
		this.categoryFacade = categoryFacade;
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

	public void setClassificationService(final ClassificationService classificationService)
	{
		this.classificationService = classificationService;
	}
}
