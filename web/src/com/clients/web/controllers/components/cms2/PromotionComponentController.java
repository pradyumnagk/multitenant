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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.clients.web.facades.CategoryFacade;
import com.clients.web.facades.ProductFacade;
import com.clients.web.helpers.ComponentAttrHelper;
import com.sun.xml.internal.ws.util.StringUtils;


public class PromotionComponentController extends AbstractCMSComponentController<PromotionComponentModel1>
{

	protected final static Logger LOG = Logger.getLogger(PromotionComponentController.class.getName());

	protected ProductFacade productFacade;
	protected CategoryFacade categoryFacade;
	protected ComponentAttrHelper componentAttrHelper;

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final PromotionComponentModel1 component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		final MediaModel media = component.getPromotion().getProductBanner();
		if (media != null)
		{
			final String url = media.getURL();
			final String name = media.getRealfilename();
			if (!StringUtils.isEmpty(url))
			{
				model.put("url", url);
				model.put("name", name);
			}
		}

		List<ProductModel> products = null;
		products = new ArrayList<ProductModel>(component.getPromotion().getProducts());
		final Collection<CategoryModel> categories = component.getPromotion().getCategories();

		// get all products of the categories
		if (!categories.isEmpty())
		{
			for (final CategoryModel category : categories)
			{
				products.addAll(category.getProducts());
			}
		}

		if (component.getPromotion() instanceof ProductOneToOnePerfectPartnerPromotionModel)
		{
			final ProductModel partnerProduct = ((ProductOneToOnePerfectPartnerPromotionModel) component.getPromotion())
					.getPartnerProduct();
			if (partnerProduct != null)
			{
				products.add(partnerProduct);
			}
		}

		if (component.getPromotion() instanceof ProductPerfectPartnerBundlePromotionModel)
		{
			final Collection<ProductModel> partnerProducts = ((ProductPerfectPartnerBundlePromotionModel) component.getPromotion())
					.getPartnerProducts();
			if (partnerProducts != null)
			{
				products.addAll(partnerProducts);
			}
		}

		if (component.getPromotion() instanceof ProductPerfectPartnerPromotionModel)
		{
			final Collection<ProductModel> partnerProducts = ((ProductPerfectPartnerPromotionModel) component.getPromotion())
					.getPartnerProducts();
			if (!partnerProducts.isEmpty())
			{
				products.addAll(partnerProducts);
			}
		}

		// put the product list into the request
		componentAttrHelper.setProductList(request, products);

		model.put("promotionName", component.getPromotion().getTitle());
		model.put("description", component.getPromotion().getDescription());

		return new ModelAndView(getView(), model);
	}

	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	public void setCategoryFacade(final CategoryFacade categoryFacade)
	{
		this.categoryFacade = categoryFacade;
	}

	public void setComponentAttrHelper(final ComponentAttrHelper componentAttrHelper)
	{
		this.componentAttrHelper = componentAttrHelper;
	}
}
