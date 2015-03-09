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
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.CatalogPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.pages.ProductPageModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfigService;
import de.hybris.platform.solrfacetsearch.config.exceptions.FacetConfigServiceException;
import de.hybris.platform.solrfacetsearch.search.Facet;
import de.hybris.platform.solrfacetsearch.search.FacetSearchService;
import de.hybris.platform.solrfacetsearch.search.FacetValue;
import de.hybris.platform.solrfacetsearch.search.QueryField;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;
import com.clients.web.data.CategoryData;
import com.clients.web.data.FacetCategoryData;
import com.clients.web.data.FacetValueData;
import com.clients.web.data.PaginationData;
import com.clients.web.data.ProductData;
import com.clients.web.data.ProductResultData;
import com.clients.web.facades.ProductFacade;
import com.clients.web.facades.SearchFacade;
import com.clients.web.helpers.CategoryHelper;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.ProductHelper;
import com.clients.web.helpers.SearchHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class DefaultSearchFacade implements SearchFacade
{

	protected final static Logger LOG = Logger.getLogger(DefaultSearchFacade.class.getName());

	protected I18NService i18nService;
	protected SearchHelper searchHelper;
	protected CategoryService categoryService;
	protected CatalogService catalogService;
	protected FacetSearchService facetSearchService;
	protected FacetSearchConfigService facetSearchConfigService;
	protected PageHelper pageHelper;
	protected String searchPageLabel;
	protected CategoryHelper categoryHelper;
	protected ProductHelper productHelper;
	protected CMSSiteService cmsSiteService;
	protected ProductFacade productFacade;

	/**
	 * Converts current query to string representation
	 */
	@Override
	public String getQueryString(final HttpServletRequest request)
	{
		final SearchQuery query = searchHelper.getCurrentSearchQuery(request);
		return (query == null) ? "" : searchHelper.convert(query);
	}

	/**
	 * Performs search
	 */
	@Override
	public SearchResult search(final SearchQuery query)
	{
		try
		{
			return facetSearchService.search(query);
		}
		catch (final Exception e)
		{
			LOG.warn("Error executing search: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Gets products from search results
	 */
	@Override
	public ProductResultData getProducts(final HttpServletRequest request, final PaginationData paginationData)
	{
		final SearchResult searchResult = getSearchResult(request, paginationData);
		return getProductResultFromSearch(searchResult, paginationData);
	}

	public ProductResultData getProductsWithSimpleSearch(final String queryString, final PaginationData paginationData)
	{
		return getProductsWithSimpleSearch(null, queryString, paginationData);
	}

	public ProductResultData getProductsWithSimpleSearch(final HttpServletRequest request, final String queryString,
			final PaginationData paginationData)
	{
		SearchQuery searchQuery = prepareSearchQuery();
		if (searchQuery == null)
		{
			return new ProductResultData();
		}
		searchQuery = addPaginationToQuery(searchQuery, paginationData);
		searchQuery.search(queryString);
		final SearchResult searchResult = search(searchQuery);
		if (request != null)
		{
			searchQuery.clearAllFields();
			searchHelper.setCurrentSearchQuery(request, searchQuery);
		}
		return getProductResultFromSearch(searchResult, paginationData);
	}

	private ProductResultData getProductResultFromSearch(final SearchResult searchResult, final PaginationData pagination)
	{
		final ProductResultData data = new ProductResultData();
		data.setOrderKey(pagination.getOrderKey());
		data.setPage(pagination.getPageNumber());
		data.setPageSize(pagination.getPageSize());
		if (searchResult == null)
		{
			return data;
		}
		final long size = searchResult.getTotalNumberOfResults();
		final int pageSize = searchResult.getPageSize();
		final long pageCount = size / pageSize + ((size % pageSize != 0) ? 1 : 0);

		data.setProducts(getProducts(searchResult));
		data.setNumberOfResults(searchResult.getTotalNumberOfResults());
		data.setPageCount(pageCount);
		return data;
	}

	@Override
	public List<FacetCategoryData> getFacetCategories(final HttpServletRequest request, final PaginationData paginationData)
	{
		final List<FacetCategoryData> facets = new ArrayList<FacetCategoryData>();
		final SearchResult searchResult = getSearchResult(request, paginationData);
		if (searchResult != null)
		{
			for (final Facet facet : searchResult.getFacets())
			{
				final FacetCategoryData data = new FacetCategoryData();
				data.setName(facet.getName());
				data.setFacets(convertFacetValues(facet, searchHelper.getCurrentSearchQuery(request)));
				facets.add(data);
			}
		}
		return facets;

	}

	private Collection<FacetValueData> convertFacetValues(final Facet facet, final SearchQuery query)
	{
		if (facet.getFacetValues().isEmpty())
		{
			return Collections.EMPTY_LIST;
		}
		final List<FacetValueData> result = new ArrayList<FacetValueData>();
		final boolean categoryFacet = "category".equalsIgnoreCase(facet.getName());
		final CatalogModel catalogModel = query.getCatalogVersion().getCatalog();
		final CatalogVersionModel catVersion = catalogService.getSessionCatalogVersion(catalogModel.getId());
		final Locale currentLocale = this.i18nService.getCurrentLocale();
		for (final FacetValue fv : facet.getFacetValues())
		{
			if (categoryFacet)
			{
				CategoryModel category = null;
				try
				{
					category = this.categoryService.getCategory(catVersion, fv.getName());
				}
				catch (final UnknownIdentifierException uie)
				{
					try
					{
						category = this.categoryService.getCategory(fv.getName());
					}
					catch (final UnknownIdentifierException e)
					{
						LOG.error(e.getMessage());
					}
				}
				if (category != null)
				{
					final String categoryName = category.getName(currentLocale);
					result.add(new FacetValueData(fv.getName(), categoryName, fv.getCount(), fv.isSelected()));
				}
			}
			else
			{
				result.add(new FacetValueData(fv.getName(), fv.getCount(), fv.isSelected()));
			}
		}
		if (categoryFacet)
		{
			Collections.sort(result);
		}
		return result;
	}

	private List<ProductData> getProducts(final SearchResult searchResult)
	{
		if (searchResult == null)
		{
			return Collections.EMPTY_LIST;
		}
		final CatalogVersionModel version = cmsSiteService.getCurrentCatalogVersion();
		return productFacade.getProducts(version.getCatalog().getId(), searchResult.getIdentifiers());
	}

	private SearchResult getSearchResult(final HttpServletRequest request, final PaginationData paginationData)
	{
		SearchResult result = searchHelper.getCurrentSearchResult(request);
		if (result == null)
		{
			LOG.debug("SearchResult is null. Creating a new search");
			final SearchQuery searchQuery = prepareSearchQuery(request);
			if (searchQuery == null)
			{
				return null;
			}
			if (paginationData != null && !searchQuery.isEmpty())
			{
				addPaginationToQuery(searchQuery, paginationData);
			}
			result = search(searchQuery);
			searchHelper.setCurrentSearchQuery(request, searchQuery);
			searchHelper.setCurrentSearchResult(request, result);
		}
		return result;
	}

	private SearchQuery addPaginationToQuery(final SearchQuery searchQuery, final PaginationData paginationData)
	{
		if (paginationData.isOrderKeyAvailable())
		{
			final String orderKey = paginationData.getOrderKey();
			final String orderField = StringUtils.substringBeforeLast(orderKey, "_");
			final boolean ascending = "up".equals(StringUtils.substringAfterLast(orderKey, "_"));
			LOG.debug("orderBy: " + orderField + " " + ((ascending) ? "ascending" : "descending"));
			searchQuery.clearOrderFields();
			searchQuery.addOrderField(orderField, ascending);
		}
		//tricky. multitenant has page = 1 for first page which is an offset of 0 in the facetsearch, so -1
		searchQuery.setOffset(paginationData.getPageNumber() - 1);
		searchQuery.setPageSize(paginationData.getPageSize());
		return searchQuery;
	}

	private SearchQuery prepareSearchQuery(final HttpServletRequest request)
	{
		//is a search query already available?
		SearchQuery query = searchHelper.getCurrentSearchQuery(request);

		if (query == null)
		{
			//create a new search query
			query = prepareSearchQuery();
			if (query == null)
			{
				return null;
			}
		}

		//all fields should be cleared
		if (request.getParameter(CLEAR_ALL_FIELDS) != null)
		{
			LOG.debug("clearAll is true");
			query.clearAllFields();
		}

		//now there are several conditions when the search query should be cleared. This is very project specific.
		//In our scenario we want the following behaviour:
		//-> clear the search query when:
		//--> (1) when we are on any content page which is not the search page (see searchPageLabel)
		//--> (2) we are on a product page and the referrer is not from the search page (clear and add the current product to the search result)
		//--> (3) when we are on a category page (clear and add the current category to the search result)
		//--> (4) when we are on a catalog page
		//--> (5) when we are on a product page
		final AbstractPageModel page = pageHelper.getCurrentPage(request);
		if (page instanceof ContentPageModel)
		{
			//check if we are on the search page
			if (!searchPageLabel.equals(((ContentPageModel) page).getLabel()))
			{
				LOG.debug("On a ContentPage. Resetting SearchQuery");
				query.clearAllFields();
			}
		}
		if (page instanceof ProductPageModel)
		{
			LOG.debug("ProductPage. Resetting SearchQuery, adding product code to SearchQuery");
			query.clearAllFields();
			updateI18N(query);
			final ProductData product = this.productHelper.getCurrentProduct(request);
			query.addFacetValue("code", product.getCode());
		}
		else if (categoryHelper.getCurrentCategory(request) != null && !comesFromSearch(request))
		{
			LOG.debug("CategoryPage. Resetting SearchQuery, adding category path to SearchQuery");
			query.clearAllFields();
			updateI18N(query);
			final CategoryData category = categoryHelper.getCurrentCategory(request);
			addCategoryPathToSearchQuery(query, category.getPath());
		}
		else if (page instanceof CatalogPageModel)
		{
			LOG.debug("CatalogPage. Resetting SearchQuery");
			query = prepareSearchQuery();
			if (query == null)
			{
				return null;
			}
		}

		checkI18N(query);

		//add search params (if there are any...)
		final String facet = request.getParameter(FACET);
		final String value = request.getParameter(VALUE);
		final String method = request.getParameter(METHOD);
		//YXX facet can be null, use the search(String) method
		if (!StringUtils.isEmpty(facet) && !StringUtils.isEmpty(value) && !StringUtils.isEmpty(method))
		{
			final String whitespace = " ";
			if ("add".equals(method) && !query.getAllFields().contains(facet))
			{

				if (value.indexOf(whitespace) == -1)
				{
					query.addFacetValue(facet, value);
				}
				else
				{
					query.addFacetValue(facet, convertWhiteSpaceValue(value));
				}
			}
			else if ("rem".equals(method))
			{
				for (final QueryField queryField : query.getAllFields())
				{
					if (queryField.getField().equals(facet))
					{
						if (value.indexOf(whitespace) == -1)
						{
							query.removeFacetValue(facet, value);
						}
						else
						{
							query.removeFacetValue(facet, convertWhiteSpaceValue(value));
						}
						break;
					}
				}
			}
		}
		return query;
	}

	private String convertWhiteSpaceValue(final String value)
	{
		return "\"" + value + "\"";
	}

	private void updateI18N(final SearchQuery query)
	{
		query.setLanguage(this.i18nService.getCurrentLocale().getLanguage());
		query.setCurrency(this.i18nService.getCurrentCurrency().getIsocode());
	}

	private void checkI18N(final SearchQuery query)
	{
		final String sessionLanguage = this.i18nService.getCurrentLocale().getLanguage();
		if (!sessionLanguage.equals(query.getLanguage()))
		{
			query.clearAllFields();
			query.setLanguage(sessionLanguage);
		}
		else
		{
			final String sessionCurrency = this.i18nService.getCurrentCurrency().getIsocode();
			if (!sessionCurrency.equals(query.getCurrency()))
			{
				query.setCurrency(sessionCurrency);
			}
		}
	}

	private void addCategoryPathToSearchQuery(final SearchQuery query, final List<String> categoryPath)
	{
		if (categoryPath != null)
		{
			for (final String element : categoryPath)
			{
				query.addFacetValue("category", element);
			}
		}
	}

	private boolean comesFromSearch(final HttpServletRequest request)
	{
		final String referer = request.getHeader("referer");
		if (referer == null)
		{
			return false;
		}
		try
		{
			final URL url = new URL(referer);
			final String[] path = StringUtils.split(url.getPath(), "/");
			final String label = path[path.length - 1];
			return searchPageLabel.equals(label);
		}
		catch (final MalformedURLException e)
		{
			LOG.warn("Could not create referer URL" + e);
		}
		return false;

	}

	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	public void setSearchHelper(final SearchHelper searchHelper)
	{
		this.searchHelper = searchHelper;
	}

	public void setFacetSearchService(final FacetSearchService facetSearchService)
	{
		this.facetSearchService = facetSearchService;
	}

	public void setSolrFacetSearchService(final FacetSearchService facetSearchService)
	{
		this.facetSearchService = facetSearchService;
	}

	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}

	public void setSearchPageLabel(final String searchPageLabel)
	{
		this.searchPageLabel = searchPageLabel;
	}

	@Override
	public String getSearchPageLabel()
	{
		return searchPageLabel;
	}

	public void setProductHelper(final ProductHelper productHelper)
	{
		this.productHelper = productHelper;
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	public void setFacetSearchConfigService(final FacetSearchConfigService facetSearchConfigService)
	{
		this.facetSearchConfigService = facetSearchConfigService;
	}

	public void setCatalogService(final CatalogService catalogService)
	{
		this.catalogService = catalogService;
	}

	/**
	 * Create an empty search query object
	 * 
	 * @return new search query object
	 */
	private SearchQuery prepareSearchQuery()
	{
		final CatalogVersionModel catVersion = cmsSiteService.getCurrentCatalogVersion();
		FacetSearchConfig facetSerachConfig = null;
		try
		{
			facetSerachConfig = facetSearchConfigService.getConfiguration(catVersion);
		}
		catch (final FacetConfigServiceException e)
		{
			LOG.error("Error while retrieving facet configuration ", e);
		}
		if (facetSerachConfig == null)
		{
			LOG.error("No solr facet configuration found. Check the catalog version [" + catVersion.getCatalog().getId()
					+ catVersion.getVersion() + "] please.");
			return null;
		}
		final SearchQuery searchQuery = new SearchQuery(facetSerachConfig, facetSerachConfig.getIndexConfig().getIndexedTypes()
				.iterator().next());
		searchQuery.setCatalogVersion(catVersion);

		return searchQuery;
	}

}
