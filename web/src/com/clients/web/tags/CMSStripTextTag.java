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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class CMSStripTextTag extends TagSupport
{

	protected final static Logger LOG = Logger.getLogger(CMSStripTextTag.class.getName());

	protected static final int MAX_LENGTH = 20;
	protected static final String PREFIX = "...";

	protected String message;
	protected int length;
	protected String prefix;
	protected boolean usePrefix;

	public CMSStripTextTag()
	{
		super();
		init();
	}

	private void init()
	{
		this.message = null;
		this.length = MAX_LENGTH;
		this.prefix = PREFIX;
		this.usePrefix = true;
	}

	@Override
	public int doStartTag() throws JspException
	{
		if (message == null)
		{
			return SKIP_BODY;
		}
		try
		{
			final JspWriter out = pageContext.getOut();
			if (message.length() <= length)
			{
				out.println(message);
			}
			else
			{
				out.println(StringUtils.substring(message, 0, length) + ((usePrefix) ? PREFIX : ""));
			}
		}
		catch (final Exception e)
		{
			LOG.warn("Could not render: " + e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;
	}

	@Override
	public void release()
	{
		super.release();
		init();
	}

	public void setMessage(final String message)
	{
		this.message = message;
	}

	public void setLength(final int length)
	{
		this.length = length;
	}

	public void setPrefix(final String prefix)
	{
		this.prefix = prefix;
	}

	public void setUsePrefix(final boolean usePrefix)
	{
		this.usePrefix = usePrefix;
	}
}
