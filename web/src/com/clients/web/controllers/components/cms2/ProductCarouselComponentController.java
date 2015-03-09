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

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.cms2.model.pages.ProductPageModel;
import de.hybris.platform.cms2lib.enums.CarouselScroll;
import de.hybris.platform.cms2lib.model.components.ProductCarouselComponentModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import com.clients.web.converters.ProductConverter;
import com.clients.web.data.CategoryData;
import com.clients.web.data.PaginationData;
import com.clients.web.data.ProductData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.facades.ProductFacade;
import com.clients.web.helpers.CategoryHelper;
import com.clients.web.helpers.PageHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;


/**
 * The Class CarouselComponentController.
 */
public class ProductCarouselComponentController extends AbstractCMSComponentController<ProductCarouselComponentModel>
{
	private static final int MIN_ITEMS = 5;
	private CategoryFacade categoryFacade;
	private CategoryHelper categoryHelper;
	private PageHelper pageHelper;
	private ProductConverter productConverter;
	private ProductFacade productFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.clients.web.controllers.components.cms2.AbstractCMSComponentController#doHandleRequest(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * de.hybris.platform.cms2.model.contents.components.SimpleCMSComponentModel)
	 */
	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final ProductCarouselComponentModel component) throws Exception
	{
		SessionContext ctx = null;
		try
		{
			ctx = JaloSession.getCurrentSession().createLocalSessionContext();
			ctx.setAttribute("disableRestrictions", Boolean.TRUE);

			final Map<String, Object> model = new HashMap<String, Object>();
			model.put("scroll", Integer.valueOf(computeScroll(component.getScroll())));
			model.put("compUid", component.getUid());

			if (isHasCategories(component))
			{ // We have categories: first priority
				final Map<String, Object> categories = new LinkedHashMap<String, Object>();
				final Map<String, Object> products = new LinkedHashMap<String, Object>();
				model.put("hasCategories", Boolean.TRUE);
				List<ProductData> ctgProducts;
				for (final CategoryModel categoryModel : component.getCategories())
				{
					ctgProducts = productFacade.getProductsFromCategory(categoryFacade.getCategory(categoryModel),
							new PaginationData()).getProducts();
					products.put(categoryModel.getPk().toString(), adjustProducts(ctgProducts));
					categories.put(categoryModel.getPk().toString(), categoryModel.getName());
				}
				model.put("products", products);
				model.put("categories", categories);
			}
			else if (isHasProducts(component))
			{ // We have products: second priority
				final List<ProductData> products = productConverter.convertAll(component.getProducts());
				model.put("hasProducts", Boolean.TRUE);
				for (final ProductData productData : products)
				{
					productData.setPath(productFacade.getPathForProduct(productData));
				}
				model.put("products", adjustProducts(products));
			}
			else
			{ // No products or categories: third priority
				final AbstractPageModel page = pageHelper.getCurrentPage(request);
				if (page instanceof CategoryPageModel)
				{ // We are on Category page - use products from current category
					final CategoryData currentCategory = categoryHelper.getCurrentCategory(request);
					final List<ProductData> products = productFacade.getProductsFromCategory(currentCategory, new PaginationData())
							.getProducts();
					if (!products.isEmpty())
					{
						model.put("hasProducts", Boolean.TRUE);
						model.put("products", adjustProducts(products));
					}
				}
				else if (page instanceof ProductPageModel)
				{// We are on Product page - use products from current product category
					final CategoryData currentCategory = categoryHelper.getCurrentCategory(request);
					final List<ProductData> products = productFacade.getProductsFromCategory(currentCategory, new PaginationData())
							.getProducts();
					if (!products.isEmpty())
					{
						model.put("hasProducts", Boolean.TRUE);
						model.put("products", adjustProducts(products));
					}
				}
			}

			return new ModelAndView(view, model);
		}
		finally
		{
			if (ctx != null)
			{
				JaloSession.getCurrentSession().removeLocalSessionContext();
			}
		}
	}

	/**
	 * Sets the category facade.
	 * 
	 * @param categoryFacade
	 *           the categoryFacade to set
	 */
	public void setCategoryFacade(final CategoryFacade categoryFacade)
	{
		this.categoryFacade = categoryFacade;
	}

	/**
	 * Sets the category helper.
	 * 
	 * @param categoryHelper
	 *           the categoryHelper to set
	 */
	public void setCategoryHelper(final CategoryHelper categoryHelper)
	{
		this.categoryHelper = categoryHelper;
	}

	/**
	 * Sets the page helper.
	 * 
	 * @param pageHelper
	 *           the pageHelper to set
	 */
	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}


	/**
	 * Sets the product converter.
	 * 
	 * @param productConverter
	 *           the productConverter to set
	 */
	public void setProductConverter(final ProductConverter productConverter)
	{
		this.productConverter = productConverter;
	}

	/**
	 * Sets the product facade.
	 * 
	 * @param productFacade
	 *           the productFacade to set
	 */
	public void setProductFacade(final ProductFacade productFacade)
	{
		this.productFacade = productFacade;
	}

	private boolean isHasCategories(final ProductCarouselComponentModel source)
	{
		if (source.getCategories().isEmpty())
		{
			return false;
		}
		return true;
	}

	private boolean isHasProducts(final ProductCarouselComponentModel source)
	{
		if (source.getProducts().isEmpty())
		{
			return false;
		}
		return true;
	}

	private List<ProductData> adjustProducts(final List<ProductData> products)
	{
		final int size = products.size();
		if (size < MIN_ITEMS && size > 0)
		{
			final int toAdd = MIN_ITEMS - size;
			for (int i = 0; i < toAdd; i++)
			{
				products.add(size + i, products.get(i));
			}
		}
		return products;
	}

	private int computeScroll(final CarouselScroll scroll)
	{
		if (scroll == null)
		{
			return 3;
		}
		return scroll.getCode().equals("one") ? 1 : 3;
	}
}
