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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.clients.web.data.CategoryData;
import com.clients.web.data.FacetCategoryData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.facades.SearchFacade;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.CategoryHelper;
import com.clients.web.helpers.PaginationHelper;
import com.clients.web.helpers.SearchHelper;
import com.clients.web.helpers.URLHelper;
import com.clients.web.interceptors.SolrFacetSearchInterceptor;


public class CategoryTreeComponentController extends AbstractCMSComponentController<CategoryTreeComponentModel1>
{

	protected final static Logger LOG = Logger.getLogger(CategoryTreeComponentController.class.getName());

	public final static String IS_FACET = "isFacet";

	protected CategoryHelper categoryHelper;
	protected CatalogHelper catalogHelper;
	protected URLHelper urlHelper;
	protected SearchHelper searchHelper;
	protected CategoryFacade categoryFacade;
	protected SearchFacade searchFacade;
	protected PaginationHelper paginationHelper;


	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final CategoryTreeComponentModel1 component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		if (component.isUseFacets() /* && searchHelper.getFacetSearchServiceFromCurrentCatalogVersion()!= null */)
		{
			model.put(IS_FACET, Boolean.TRUE);
			final String facets = "facets";
			final String allFacetsEmpty = "allFacetsEmpty";
			final Boolean legalSolrSearch = (Boolean) request.getSession()
					.getAttribute(SolrFacetSearchInterceptor.LEGAL_SOLR_SEARCH);
			if (legalSolrSearch == Boolean.TRUE)
			{
				final List<FacetCategoryData> facetCategories = searchFacade.getFacetCategories(request,
						paginationHelper.getPaginationDataForSearch(request));
				model.put(facets, facetCategories);
				Boolean facetsEmpty = Boolean.TRUE;
				for (final FacetCategoryData facet : facetCategories)
				{
					if (facet.isFacetsAvailable())
					{
						facetsEmpty = Boolean.FALSE;
						break;
					}
				}
				model.put(allFacetsEmpty, facetsEmpty);
			}
			else
			{
				model.put(facets, Collections.EMPTY_LIST);
				model.put(allFacetsEmpty, Boolean.TRUE);
			}
		}
		else
		{
			model.put(IS_FACET, Boolean.FALSE);
			final CatalogModel catalog = catalogHelper.getCurrentCatalogVersion().getCatalog();
			final CategoryData currentCategory = categoryHelper.getCurrentCategory(request);
			if (currentCategory == null)
			{
				model.put("categories", categoryFacade.getRootCategories());
			}
			else
			{
				final CategoryData parent = categoryFacade.getParentCategory(currentCategory);
				if (parent == null)
				{
					model.put("backLinkName", catalog.getName());
					model.put("backLinkLink", urlHelper.getURLForCatalogPage(catalog));
				}
				else
				{
					model.put("backLinkName", parent.getName());
					model.put("backLinkLink", urlHelper.getURLForCategoryPage(parent));
				}

				if (categoryFacade.hasSubCategories(currentCategory))
				{
					model.put("treeHeadline", currentCategory.getName());
					model.put("categories", categoryFacade.getSubCategories(currentCategory));
				}
				else if (parent != null)
				{
					model.put("treeHeadline", parent.getName());
					model.put("categories", categoryFacade.getSubCategories(parent));
					model.put("selectedCategory", currentCategory);
				}
				else
				{
					model.put("categories", categoryFacade.getRootCategories());
					model.put("selectedCategory", currentCategory);
				}
			}
		}
		return new ModelAndView(getView(), model);
	}

	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}

	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

	public void setSearchHelper(final SearchHelper searchHelper)
	{
		this.searchHelper = searchHelper;
	}

	public void setCategoryFacade(final CategoryFacade categoryFacade)
	{
		this.categoryFacade = categoryFacade;
	}

	public void setSearchFacade(final SearchFacade searchFacade)
	{
		this.searchFacade = searchFacade;
	}

	public void setPaginationHelper(final PaginationHelper paginationHelper)
	{
		this.paginationHelper = paginationHelper;
	}

}
