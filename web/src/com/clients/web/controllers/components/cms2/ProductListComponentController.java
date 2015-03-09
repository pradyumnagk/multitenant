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
package com.clients.web.controllers.components.cms2;

import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.cms2lib.model.components.ProductListComponentModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.search.SearchResult;
import com.clients.web.data.CategoryData;
import com.clients.web.data.PaginationData;
import com.clients.web.data.ProductResultData;
import com.clients.web.facades.ProductFacade;
import com.clients.web.facades.SearchFacade;
import com.clients.web.helpers.CategoryHelper;
import com.clients.web.helpers.ComponentAttrHelper;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.PaginationHelper;
import com.clients.web.helpers.ProductHelper;
import com.clients.web.helpers.SearchHelper;
import com.clients.web.helpers.URLHelper;
import com.clients.web.interceptors.SolrFacetSearchInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;


public class ProductListComponentController extends AbstractCMSComponentController<ProductListComponentModel>
{

	protected final static Logger LOG = Logger.getLogger(ProductListComponentController.class.getName());

	protected PageHelper pageHelper;
	protected SearchHelper searchHelper;
	protected SearchFacade searchFacade;
	protected ProductFacade productFacade;
	protected CategoryHelper categoryHelper;
	protected PaginationHelper paginationHelper;
	protected URLHelper urlHelper;
	protected ProductHelper productHelper;
	protected ComponentAttrHelper componentAttrHelper;

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final ProductListComponentModel component) throws Exception
	{
		//YXX with and without search text
		final Map<String, Object> model = new HashMap<String, Object>();
		final boolean usePagination = component.isPagination();

		//pagination data for page pagination
		PaginationData pagination = null;
		if (usePagination)
		{
			pagination = paginationHelper.getPaginationData(request, component);
			model.put("usePagination", Boolean.TRUE);
			model.put("orderKeys", paginationHelper.getOrderKeys());
			model.put("paginationSizes", paginationHelper.getPaginationSizes());
			model.put("paginationId", pagination.getId());
		}
		else
		{
			pagination = new PaginationData();
			model.put("usePagination", Boolean.FALSE);
		}

		//headline and layout code
		model.put("headline", component.getHeadline());
		model.put("layoutId", component.getLayout().getCode());

		//get the products depending on the context information or the content element configuration
		final AbstractPageModel page = pageHelper.getCurrentPage(request);
		ProductResultData result = new ProductResultData();
		final Boolean legalSolrSearch = (Boolean) request.getSession().getAttribute(SolrFacetSearchInterceptor.LEGAL_SOLR_SEARCH);
		if (legalSolrSearch == Boolean.TRUE)
		{

			//Products from context
			if (component.isProductsFromContext())
			{
				final SearchResult searchResult = searchHelper.getCurrentSearchResult(request);

				List<ProductModel> productList = null;
				final List<ProductModel> requestProductList = componentAttrHelper.getProductList(request);

				if (requestProductList != null)
				{
					productList = requestProductList;
				}

				if (productList != null && !productList.isEmpty())
				{
					result = productFacade.getProducts(productList, pagination);
				}
				else if (searchResult != null)
				{
					//products from the searchresult
					result = searchFacade.getProducts(request, pagination);
				}
				else if (page instanceof CategoryPageModel)
				{
					//products from the current category
					final CategoryData categoryData = categoryHelper.getCurrentCategory(request);
					result = productFacade.getProductsFromCategory(categoryData, pagination);
				}
				else
				{
					LOG.info("getting products from context in a " + page.getTypeCode() + " not implemented!");
					result = new ProductResultData();
				}
			}
			else
			{
				if (!component.getProductCodes().isEmpty())
				{
					//products from content element
					result = productFacade.getProductsFromCodes(component.getProductCodes(), pagination);
				}
				else if (component.getCategoryCode() != null)
				{
					//products from category
					result = productFacade.getProductsFromCategory(component.getCategoryCode(), pagination);
				}
				else if (!StringUtils.isEmpty(component.getSearchQuery()))
				{
					//products from a search query
					result = searchFacade.getProductsWithSimpleSearch(request, component.getSearchQuery(), pagination);
				}
				else
				{
					//???
					result = new ProductResultData();
				}
			}
			if (result == null)
			{
				result = new ProductResultData();
			}
		}
		model.put("productResult", result);
		return new ModelAndView(getView(), model);
	}

	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setSearchHelper(final SearchHelper searchHelper)
	{
		this.searchHelper = searchHelper;
	}

	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	public void setSearchFacade(final SearchFacade searchFacade)
	{
		this.searchFacade = searchFacade;
	}

	public void setPaginationHelper(final PaginationHelper paginationHelper)
	{
		this.paginationHelper = paginationHelper;
	}

	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

	public void setComponentAttrHelper(final ComponentAttrHelper componentAttrHelper)
	{
		this.componentAttrHelper = componentAttrHelper;
	}
}
