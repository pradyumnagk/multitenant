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
package com.clients.web.tags;

import com.clients.web.data.ProductResultData;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


public class CMSPaginationTag extends TagSupport
{

	protected final static Logger LOG = Logger.getLogger(CMSPaginationTag.class.getName());

	protected static final String DEFAULT_STYLE = "pager";

	protected ProductResultData productResult;
	protected String baseStyleClassName = DEFAULT_STYLE;
	protected String currentURL;
	protected String paginationId;

	public CMSPaginationTag()
	{
		super();
		init();
	}

	@Override
	public int doStartTag() throws JspException
	{
		if (productResult == null)
		{
			return SKIP_BODY;
		}
		try
		{
			final JspWriter out = pageContext.getOut();
			final int pageNumber = productResult.getPage();
			final String id = paginationId;
			final long start = getStartIndex(pageNumber);
			final long pageCount = productResult.getPageCount();
			final long end = getEndIndex(pageNumber, pageCount);
			if (pageCount <= 1)
			{
				return SKIP_BODY;
			}
			out.println("<div class=\"" + baseStyleClassName + "\">");
			if (pageNumber > 3)
			{
				out.println("<a class=\"" + baseStyleClassName + "Box " + baseStyleClassName + "BoxLeftFirst\" href=\"" + currentURL
						+ "?pn_id=" + id + "&pn_p=1" + "\">|&laquo;</a>");
				out.println("<a class=\"" + baseStyleClassName + "Box " + baseStyleClassName + "BoxLeft\" href=\"" + currentURL
						+ "?pn_id=" + id + "&pn_p=" + (pageNumber - 1) + "\">&laquo;</a>");
			}
			for (long i = start; i <= end; i++)
			{
				out.println("<a class=\"" + baseStyleClassName + "Box"
						+ ((i == pageNumber) ? " " + baseStyleClassName + "BoxSelected" : "") + "\" href=\"" + currentURL + "?pn_id="
						+ id + "&pn_p=" + i + "\">" + i + "</a>");
			}
			if (end < pageCount)
			{
				out.println("<a class=\"" + baseStyleClassName + "Box " + baseStyleClassName + "BoxRight\" href=\"" + currentURL
						+ "?pn_id=" + id + "&pn_p=" + (pageNumber + 1) + "\">&raquo;</a>");
				out.println("<a class=\"" + baseStyleClassName + "Box " + baseStyleClassName + "BoxRightLast\" href=\"" + currentURL
						+ "?pn_id=" + id + "&pn_p=" + pageCount + "\">&raquo;|</a>");
			}
			out.println("</div>");
		}
		catch (final Exception e)
		{
			LOG.warn("Could not render pagination: " + e.getMessage());
		}

		return SKIP_BODY;
	}

	private long getStartIndex(final int pageNumber)
	{
		if (pageNumber <= 3)
		{
			return 1;
		}
		return pageNumber - 2;
	}

	private long getEndIndex(final int pageNumber, final long pageCount)
	{
		if (pageCount <= 5)
		{
			return pageCount;
		}
		if (pageNumber <= 3)
		{
			return 5;
		}
		if (pageNumber + 2 > pageCount)
		{
			return pageCount;
		}
		return pageNumber + 2;

	}

	@Override
	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;
	}

	public void setBaseStyleClassName(final String baseStyleClassName)
	{
		this.baseStyleClassName = baseStyleClassName;
	}

	public void setProductResult(final ProductResultData productResult)
	{
		this.productResult = productResult;
	}

	public void setCurrentURL(final String currentURL)
	{
		this.currentURL = currentURL;
	}

	public void setPaginationId(final String paginationId)
	{
		this.paginationId = paginationId;
	}

	@Override
	public void release()
	{
		super.release();
		init();
	}

	protected void init()
	{
		productResult = null;
		baseStyleClassName = DEFAULT_STYLE;
		currentURL = null;
		paginationId = null;
	}

}
