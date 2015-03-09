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
package com.clients.web.helpers.impl;

import de.hybris.platform.cms2.model.contents.components.SimpleCMSComponentModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import com.clients.web.data.PaginationData;
import com.clients.web.facades.SearchFacade;
import com.clients.web.helpers.PageHelper;
import com.clients.web.helpers.PaginationHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


public class DefaultPaginationHelper implements PaginationHelper
{

	protected PageHelper pageHelper;
	protected SearchFacade searchFacade;

	protected final static String PAGINATION_VALUES = "paginationValues";
	protected final static String ELEMENT_ID = "pn_id";
	protected final static String ORDER_KEY = "pn_ok";
	protected final static String PAGINATIONSIZE_KEY = "pn_ps";
	protected final static String PAGE_KEY = "pn_p";

	protected List paginationSizes;
	protected List orderKeys;

	private String createElementId(final HttpServletRequest request, final SimpleCMSComponentModel component)
	{
		if (pageHelper.isCurrentPageContentPage(request))
		{
			final ContentPageModel page = (ContentPageModel) pageHelper.getCurrentPage(request);
			final String searchPageLabel = searchFacade.getSearchPageLabel();
			//if we are on a search page, always use the same id
			if (searchPageLabel.equals(page.getLabelOrId()))
			{
				return searchPageLabel;
			}
		}
		final String id = (String) request.getAttribute("currentURL") + component.getUid();
		return DigestUtils.md5Hex(id);

	}

	private PaginationData getPaginationData(final HttpServletRequest request, final String id)
	{
		final Map<String, PaginationData> datas = getPaginationData(request);
		if (datas.containsKey(id))
		{
			PaginationData data = datas.get(id);
			data = setPaginationValues(id, data, request);
			setPaginationData(request, id, data);
			return data;
		}
		final PaginationData data = new PaginationData();
		data.setId(id);
		data.setPageNumber(1);
		data.setPageSize(getDefaultPageSize());
		data.setOrderKey(getDefaultOrderKey());
		setPaginationData(request, id, data);
		return data;
	}

	@Override
	public PaginationData getPaginationDataForSearch(final HttpServletRequest request)
	{
		return getPaginationData(request, searchFacade.getSearchPageLabel());
	}

	@Override
	public PaginationData getPaginationData(final HttpServletRequest request, final SimpleCMSComponentModel component)
	{
		return getPaginationData(request, createElementId(request, component));

	}

	private PaginationData setPaginationValues(final String id, final PaginationData data, final HttpServletRequest request)
	{
		final String eid = request.getParameter(ELEMENT_ID);
		if (id.equals(eid))
		{
			final String order = request.getParameter(ORDER_KEY);
			if (!StringUtils.isEmpty(order))
			{
				data.setOrderKey(order);
			}
			final String size = request.getParameter(PAGINATIONSIZE_KEY);
			if (!StringUtils.isEmpty(size))
			{
				if (StringUtils.isNumeric(size))
				{
					data.setPageSize(Integer.parseInt(size));
				}
				else
				{
					data.setPageSize(Integer.MAX_VALUE);
				}
				data.setPageNumber(1); //set pagenumber to 1 when pagination size has changed
			}
			else
			{
				//only set a new page size when the pagination size was NOT changed
				final String page = request.getParameter(PAGE_KEY);
				if (!StringUtils.isEmpty(page) && StringUtils.isNumeric(page))
				{
					data.setPageNumber(Integer.parseInt(page));
				}
			}
		}
		return data;
	}

	private void setPaginationData(final HttpServletRequest request, final String id, final PaginationData paginationData)
	{
		final Map<String, PaginationData> data = getPaginationData(request);
		data.put(id, paginationData);
		setPaginationData(request, data);
	}

	private Map<String, PaginationData> getPaginationData(final HttpServletRequest request)
	{
		Map<String, PaginationData> data = (Map<String, PaginationData>) request.getSession().getAttribute(PAGINATION_VALUES);
		if (data == null)
		{
			data = new HashMap<String, PaginationData>();
		}
		return data;
	}

	private int getDefaultPageSize()
	{
		final String value = (String) paginationSizes.get(0);
		if (StringUtils.isNumeric(value))
		{
			return Integer.parseInt(value);
		}
		return Integer.MAX_VALUE;
	}

	private String getDefaultOrderKey()
	{
		return (String) orderKeys.get(0) + "_up";
	}

	@Override
	public List<String> getPaginationSizes()
	{
		return paginationSizes;
	}

	@Override
	public List<String> getOrderKeys()
	{
		return orderKeys;
	}

	private void setPaginationData(final HttpServletRequest request, final Map<String, PaginationData> data)
	{
		request.getSession().setAttribute(PAGINATION_VALUES, data);
	}

	public void setPaginationSizes(final List paginationSizes)
	{
		this.paginationSizes = paginationSizes;
	}

	public void setOrderKeys(final List orderKeys)
	{
		this.orderKeys = orderKeys;
	}

	public void setPageHelper(final PageHelper pageHelper)
	{
		this.pageHelper = pageHelper;
	}

	public void setSearchFacade(final SearchFacade searchFacade)
	{
		this.searchFacade = searchFacade;
	}

}
