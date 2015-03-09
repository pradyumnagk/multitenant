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

import de.hybris.platform.cms2.constants.Cms2Constants;
import de.hybris.platform.cms2.model.contents.components.CMSLinkComponentModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import com.clients.web.data.CategoryData;
import com.clients.web.data.ProductData;
import com.clients.web.facades.CategoryFacade;
import com.clients.web.facades.ProductFacade;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.URLHelper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;


public class CMSLinkComponentController extends AbstractCMSComponentController<CMSLinkComponentModel>
{

	private ProductFacade productFacade;
	private CategoryFacade categoryFacade;
	private URLHelper urlHelper;
	private PageHelper pageHelper;

	@Override
	public ModelAndView doHandleRequest(final HttpServletRequest request, final HttpServletResponse response,
			final CMSLinkComponentModel component) throws Exception
	{
		final Map<String, Object> model = new HashMap<String, Object>();
		final String tCode = component.getTarget().getCode();
		final String target = (Cms2Constants.Enumerations.LinkTargets.NEWWINDOW.equals(tCode)) ? "_blank" : "_self";
		String href = null;
		String linklabel = component.getLinkName();

		if (component.isExternal())
		{
			href = component.getUrl();
			if (StringUtils.isBlank(linklabel))
			{
				linklabel = href;
			}
		}
		else if (component.getContentPageLabelOrId() != null)
		{
			final AbstractPageModel contentPageModel = pageHelper.getPageByLabelOrIdSafe(component.getContentPageLabelOrId());
			if (contentPageModel instanceof ContentPageModel)
			{
				href = request.getContextPath() + urlHelper.getURLForContentPage((ContentPageModel) contentPageModel);
				if (StringUtils.isBlank(linklabel))
				{
					linklabel = component.getContentPageLabelOrId();
				}
			}
		}
		else if (component.getProductCode() != null)
		{
			final ProductData productData = productFacade.getProduct(component.getProductCode());
			if (productData != null)
			{
				href = request.getContextPath() + urlHelper.getURLForProductPage(productData);
				if (StringUtils.isBlank(linklabel))
				{
					linklabel = component.getProductCode();
				}
			}
		}
		else if (component.getCategoryCode() != null)
		{
			final CategoryData categoryData = categoryFacade.getCategory(component.getCategoryCode());
			if (categoryData != null)
			{
				href = request.getContextPath() + urlHelper.getURLForCategoryPage(categoryData);
				if (StringUtils.isBlank(linklabel))
				{
					linklabel = component.getCategoryCode();
				}
			}
		}
		else
		{
			href = "#";
		}
		model.put("linklabel", linklabel);
		model.put("target", target);
		model.put("href", href);
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

	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}

}
