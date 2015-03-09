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
package com.clients.web.controllers.pages;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.servicelayer.data.ContentSlotData;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import com.clients.web.facades.CartFacade;
import com.clients.web.facades.CatalogFacade;
import com.clients.web.helpers.CatalogHelper;
import com.clients.web.helpers.ComponentAttrHelper;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.URLHelper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public abstract class AbstractPageController implements Controller
{

	protected final static Logger LOG = Logger.getLogger(AbstractPageController.class.getName());

	protected final static String DEFAULT_VIEW = "default";
	protected final static String SLOT_PREFIX = "cs_";

	protected Map<String, String> views;

	protected CMSSiteService cmsSiteService;
	protected CMSPageService cmsPageService;

	protected CartFacade cartFacade;
	protected CatalogFacade catalogFacade;

	protected CatalogHelper catalogHelper;
	protected PageHelper pageHelper;
	protected URLHelper urlHelper;
	protected ComponentAttrHelper componentAttrHelper;
	private ResourcePathProvider resourcePathProvider;


	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		final String resourcePath = resourcePathProvider.getResourcePath(request);
		final AbstractPageModel page = getPage(resourcePath, request, response);

		final Map<String, Object> model = new HashMap<String, Object>();
		// page name
		model.put("templateName", page.getMasterTemplate().getName());

		componentAttrHelper.createComponentAttributes(request);

		//set the current site
		model.put("site", cmsSiteService.getCurrentSite());

		//get the view of the page
		final String view = getView(page);

		//set the page
		pageHelper.setCurrentPage(request, page);

		// all content slots for default page
		model.put("contentSlots", cmsPageService.getContentSlotsForPage(page));

		//set all content slots
		for (final ContentSlotData contentSlot : cmsPageService.getContentSlotsForPage(page))
		{
			final String pos = SLOT_PREFIX + contentSlot.getPosition();
			model.put(pos, contentSlot.getContentSlot());
		}
		//some other request attributes, useful for other content elements or view elements
		model.put("currentURL", urlHelper.getCurrentURL(request));
		model.put("contextPath", request.getContextPath());
		model.put("cartData", cartFacade.getCurrentCart());

		final CatalogVersionModel catalogVersion = catalogHelper.getCurrentCatalogVersion();
		if (catalogVersion != null)
		{
			final String catalogId = catalogVersion.getCatalog().getId();
			request.setAttribute("catalog", catalogFacade.getCatalog(catalogId));
			request.setAttribute("catalogId", catalogId);
		}
		return new ModelAndView(view, model);
	}

	protected abstract AbstractPageModel getPage(String resourcePath, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	protected String getView(final AbstractPageModel page)
	{
		final String view = page.getView();
		if (views.containsKey(view))
		{
			return views.get(view);
		}
		LOG.warn("No view defined for template [" + view + "]. Returning default view");
		return views.get(DEFAULT_VIEW);
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	public void setCmsPageService(final CMSPageService cmsPageService)
	{
		this.cmsPageService = cmsPageService;
	}

	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}

	public void setViews(final Map<String, String> views)
	{
		this.views = views;
	}

	public void setUrlHelper(final URLHelper urlHelper)
	{
		this.urlHelper = urlHelper;
	}

	public void setCatalogHelper(final CatalogHelper catalogHelper)
	{
		this.catalogHelper = catalogHelper;
	}

	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	public void setCatalogFacade(final CatalogFacade catalogFacade)
	{
		this.catalogFacade = catalogFacade;
	}

	public void setComponentAttrHelper(final ComponentAttrHelper componentAttrHelper)
	{
		this.componentAttrHelper = componentAttrHelper;
	}

	public void setResourcePathProvider(final ResourcePathProvider resourcePathProvider)
	{
		this.resourcePathProvider = resourcePathProvider;
	}
}
