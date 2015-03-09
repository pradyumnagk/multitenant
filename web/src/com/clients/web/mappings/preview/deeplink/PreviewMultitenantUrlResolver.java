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
package com.clients.web.mappings.preview.deeplink;

import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.CatalogPageModel;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.pages.ProductPageModel;
import de.hybris.platform.cms2.model.preview.PreviewDataModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.deeplink.model.rules.DeeplinkUrlModel;
import de.hybris.platform.deeplink.model.rules.DeeplinkUrlRuleModel;
import de.hybris.platform.deeplink.pojo.DeeplinkUrlInfo;
import de.hybris.platform.deeplink.resolvers.BarcodeUrlResolver;
import de.hybris.platform.deeplink.resolvers.impl.DefaultBarcodeUrlResolver;
import de.hybris.platform.deeplink.services.DeeplinkUrlService;
import de.hybris.platform.product.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Responsible for resolving preview multitenant token in order to fetch all necessary context information
 * <p/>
 * <b>Note:</b><br/>
 * Method {@link #resolve(String)} retrieves all necessary context information then creates {@link DeeplinkUrlInfo} that
 * take part in generating desired URL
 * 
 * @see BarcodeUrlResolver
 * @see DeeplinkUrlService#generateUrl(String)
 * 
 * @author karol.walczak
 * 
 */
public class PreviewMultitenantUrlResolver extends DefaultBarcodeUrlResolver
{
	public static final String TOKEN_VALUE_SEPARATOR = "-";
	private CategoryService categoryService;
	private ProductService productService;

	public CategoryService getCategoryService()
	{
		return categoryService;
	}

	/***
	 * Retrieves current catalog model from {@link PreviewDataModel} if exist otherwise getting default one from active
	 * {@link CMSSiteModel}
	 * <p/>
	 * <b>Note:</b> <br/>
	 * Active {@link CMSSiteModel} is taken from {@link PreviewDataModel#getActiveSite()}
	 * 
	 * @param previewCtx
	 *           current preview context that contains necessary information
	 * @return catalog model
	 */
	protected CatalogModel getPreviewValueForCatalogPage(final PreviewDataModel previewCtx)
	{
		final CMSSiteModel currentSite = previewCtx.getActiveSite();
		CatalogModel ret = previewCtx.getPreviewCatalog();
		if (ret == null)
		{
			if (currentSite != null)
			{
				ret = currentSite.getDefaultPreviewCatalog();
			}
		}
		return ret;
	}

	/***
	 * Retrieves current category model from {@link PreviewDataModel} if exist otherwise getting default one from active
	 * {@link CMSSiteModel}
	 * <p/>
	 * <b>Note:</b> <br/>
	 * Active {@link CMSSiteModel} is taken from {@link PreviewDataModel#getActiveSite()}
	 * 
	 * @param previewCtx
	 *           current preview context that contains necessary information
	 * @return category model
	 */
	protected CategoryModel getPreviewValueForCategoryPage(final PreviewDataModel previewCtx)
	{
		final CMSSiteModel currentSite = previewCtx.getActiveSite();
		CategoryModel ret = previewCtx.getPreviewCategory();

		if (ret == null)
		{
			if (currentSite != null)
			{
				ret = currentSite.getDefaultPreviewCategory();
			}
		}
		//ensure that we get category from session catalog version
		ret = getCategoryService().getCategory(ret.getCode());
		return ret;
	}

	/***
	 * Retrieves current product model from {@link PreviewDataModel} if exist otherwise getting default one from active
	 * {@link CMSSiteModel}
	 * <p/>
	 * <b>Note:</b> <br/>
	 * Active {@link CMSSiteModel} is taken from {@link PreviewDataModel#getActiveSite()}
	 * 
	 * @param previewCtx
	 *           current preview context that contains necessary information
	 * @return product model
	 */
	protected ProductModel getPreviewValueForProductPage(final PreviewDataModel previewCtx)
	{
		final CMSSiteModel currentSite = previewCtx.getActiveSite();
		ProductModel ret = previewCtx.getPreviewProduct();

		if (ret == null)
		{
			if (currentSite != null)
			{
				ret = currentSite.getDefaultPreviewProduct();
			}
		}
		//ensure that we get product from session catalog version
		ret = getProductService().getProduct(ret.getCode());
		return ret;
	}

	public ProductService getProductService()
	{
		return productService;
	}

	/**
	 * Loads all necessary information in order to generate proper URL for {@link CatalogPageModel}
	 * <p/>
	 * <b>Note:</b><br/>
	 * Information that will be stored within {@link DeeplinkUrlInfo} (<code>deeplinkInfo</code>) are used for generating
	 * proper URL. {@link DeeplinkUrlService#generateUrl(String)} fetches best {@link DeeplinkUrlRuleModel} and with
	 * using {@link DeeplinkUrlInfo} generates proper URL.
	 * 
	 * @see DeeplinkUrlService#generateUrl(String)
	 * 
	 * @param deeplinkInfo
	 *           deeplinkInfo object
	 * @param previewCtx
	 *           current preview context that contains necessary information
	 */
	protected void loadCatalogPageDeeplinkInfo(final PreviewDeeplinkInfo deeplinkInfo, final PreviewDataModel previewCtx)
	{
		final CatalogModel catalogModel = getPreviewValueForCatalogPage(previewCtx);
		deeplinkInfo.setCatalogId(catalogModel.getId());
	}

	/**
	 * Loads all necessary information in order to generate proper URL for {@link CategoryPageModel}
	 * <p/>
	 * <b>Note:</b><br/>
	 * Information that will be stored within {@link DeeplinkUrlInfo} (<code>deeplinkInfo</code>) are used for generating
	 * proper URL. {@link DeeplinkUrlService#generateUrl(String)} fetches best {@link DeeplinkUrlRuleModel} and with
	 * using {@link DeeplinkUrlInfo} generates proper URL.
	 * 
	 * @see DeeplinkUrlService#generateUrl(String)
	 * 
	 * @param deeplinkInfo
	 *           deeplinkInfo object
	 * @param initialCategoryModel
	 *           current category
	 */
	protected void loadCategoryPageDeeplinkInfo(final CategoryModel initialCategoryModel, final PreviewDeeplinkInfo deeplinkInfo)
	{
		final List<String> categoryPath = new ArrayList<String>();
		for (final CategoryModel categoryModel : categoryService.getPath(initialCategoryModel))
		{
			categoryPath.add(categoryModel.getCode());
		}
		Collections.reverse(categoryPath);

		deeplinkInfo.setCatalogId(initialCategoryModel.getCatalogVersion().getCatalog().getId());
		deeplinkInfo.setCategoryPathId(categoryPath);

	}

	/**
	 * Loads all necessary information in order to generate proper URL for {@link ContentPageModel}
	 * <p/>
	 * <b>Note:</b><br/>
	 * Information that will be stored within {@link DeeplinkUrlInfo} (<code>deeplinkInfo</code>) are used for generating
	 * proper URL. {@link DeeplinkUrlService#generateUrl(String)} fetches best {@link DeeplinkUrlRuleModel} and with
	 * using {@link DeeplinkUrlInfo} generates proper URL.
	 * 
	 * @see DeeplinkUrlService#generateUrl(String)
	 * 
	 * @param deeplinkInfo
	 *           deeplinkInfo object
	 * @param previewCtx
	 *           current preview context that contains necessary information
	 */
	protected void loadContetnPageDeeplinkInfo(final PreviewDeeplinkInfo deeplinkInfo, final PreviewDataModel previewCtx)
	{
		final ContentPageModel contentPage = (ContentPageModel) previewCtx.getPage();
		deeplinkInfo.setLabelOrId(contentPage.getUid());
	}

	/**
	 * Loads all necessary information in order to generate URL depending on current page type
	 * {@link PreviewDataModel#getPage()}
	 * <p/>
	 * <b>Note:</b><br/>
	 * Information that will be stored within {@link DeeplinkUrlInfo} (<code>deeplinkInfo</code>) are used for generating
	 * proper URL. {@link DeeplinkUrlService#generateUrl(String)} fetches best {@link DeeplinkUrlRuleModel} and with
	 * using {@link DeeplinkUrlInfo} generates proper URL.
	 * 
	 * @see DeeplinkUrlService#generateUrl(String)
	 * 
	 * @param deeplinkInfo
	 *           deeplinkInfo object
	 * @param previewCtx
	 *           current preview context that contains necessary information
	 */
	protected void loadPreviewDeeplinkInformation(final PreviewDeeplinkInfo deeplinkInfo, final PreviewDataModel previewCtx)
	{
		final AbstractPageModel pageModel = previewCtx.getPage();

		if (pageModel instanceof ContentPageModel)
		{
			loadContetnPageDeeplinkInfo(deeplinkInfo, previewCtx);
		}
		else if (pageModel instanceof CatalogPageModel)
		{
			loadCatalogPageDeeplinkInfo(deeplinkInfo, previewCtx);
		}
		else if (pageModel instanceof CategoryPageModel)
		{
			final CategoryModel initCategoryModel = getPreviewValueForCategoryPage(previewCtx);
			loadCategoryPageDeeplinkInfo(initCategoryModel, deeplinkInfo);
		}
		else if (pageModel instanceof ProductPageModel)
		{
			loadProductPageDeeplinkInfo(previewCtx, deeplinkInfo);
		}
	}

	/**
	 * Loads all necessary information in order to generate proper URL for {@link ProductPageModel}
	 * <p/>
	 * <b>Note:</b><br/>
	 * Information that will be stored within {@link DeeplinkUrlInfo} (<code>deeplinkInfo</code>) are used for generating
	 * proper URL. {@link DeeplinkUrlService#generateUrl(String)} fetches best {@link DeeplinkUrlRuleModel} and with
	 * using {@link DeeplinkUrlInfo} generates proper URL.
	 * 
	 * @see DeeplinkUrlService#generateUrl(String)
	 * 
	 * @param deeplinkInfo
	 *           deeplinkInfo object
	 * @param previewCtx
	 *           current preview context that contains necessary information
	 */
	protected void loadProductPageDeeplinkInfo(final PreviewDataModel previewCtx, final PreviewDeeplinkInfo deeplinkInfo)
	{
		final ProductModel initialProductModel = getPreviewValueForProductPage(previewCtx);
		final CategoryModel initialCategoryModel = initialProductModel.getSupercategories().iterator().next();
		loadCategoryPageDeeplinkInfo(initialCategoryModel, deeplinkInfo);
		deeplinkInfo.setCatalogId(initialCategoryModel.getCatalogVersion().getCatalog().getId());
		deeplinkInfo.setProductCode(initialProductModel.getCode());

	}

	/**
	 * Resolves token and finds proper items based on token values.
	 * 
	 * @param token
	 *           the token containing necessary info to fill ResolvedToken object with values
	 * @return the DeeplinkUrlInfo POJO {@link DeeplinkUrlInfo}
	 * 
	 * @see BarcodeUrlResolver
	 */
	@Override
	public DeeplinkUrlInfo resolve(final String token)
	{
		Object previewDataObject = null;
		DeeplinkUrlModel deeplinkUrlModel = null;
		PreviewDataModel previewDataModel = null;
		PreviewDeeplinkInfo previewDeeplink = null;

		final String[] splittedTokenValue = token.split(TOKEN_VALUE_SEPARATOR);

		if (splittedTokenValue.length == 2)
		{
			deeplinkUrlModel = getDeeplinkUrlDao().findDeeplinkUrlModel(splittedTokenValue[0]);
			previewDataObject = getDeeplinkUrlDao().findObject(splittedTokenValue[1]);
		}

		if (previewDataObject instanceof PreviewDataModel)
		{
			previewDataModel = (PreviewDataModel) previewDataObject;
			previewDeeplink = new PreviewDeeplinkInfo(deeplinkUrlModel, previewDataModel.getPage());
			loadPreviewDeeplinkInformation(previewDeeplink, previewDataModel);
		}

		return previewDeeplink;
	}

	public void setCategoryService(final CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}
}
