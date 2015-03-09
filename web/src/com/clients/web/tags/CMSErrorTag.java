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

import com.clients.web.data.ErrorData;
import com.clients.web.helpers.ErrorHelper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class CMSErrorTag extends BodyTagSupport
{

	protected final static Logger LOG = Logger.getLogger(CMSErrorTag.class.getName());

	private String errorId;
	private String var;
	private int scope;
	private ErrorData item;
	private boolean removeFromSession;


	public CMSErrorTag()
	{
		super();
		init();
	}

	@Override
	public void release()
	{
		super.release();
		init();
	}

	private void init()
	{
		var = null;
		errorId = null;
		item = null;
		scope = PageContext.REQUEST_SCOPE;
		removeFromSession = true;
	}

	@Override
	public int doStartTag() throws JspException
	{
		final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		final ErrorData error = getErrorHelper().getError(request, errorId);
		if (error != null)
		{
			item = error;
			exposeVariables();
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	@Override
	public int doAfterBody() throws JspException
	{
		final HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		unexposeVariables();
		if (removeFromSession)
		{
			getErrorHelper().removeError(request, errorId);
		}
		return SKIP_BODY;
	}

	protected ErrorHelper getErrorHelper()
	{
		final WebApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		return (ErrorHelper) appContext.getBean("errorHelper");
	}

	protected ServletContext getServletContext()
	{
		return pageContext.getServletContext();
	}

	private void exposeVariables()
	{
		pageContext.setAttribute(var, item, scope);
	}

	private void unexposeVariables()
	{
		pageContext.removeAttribute(var, scope);
	}

	public void setVar(final String var)
	{
		this.var = var;
	}

	public void setRemoveFromSession(final boolean removeFromSession)
	{
		this.removeFromSession = removeFromSession;
	}

	@Override
	public void setBodyContent(final BodyContent bodyContent)
	{
		this.bodyContent = bodyContent;
	}

	public void setScope(final String scope)
	{
		if (StringUtils.isEmpty(scope) || StringUtils.equalsIgnoreCase(scope, "request"))
		{
			this.scope = PageContext.REQUEST_SCOPE;
		}
		else if (StringUtils.equalsIgnoreCase(scope, "page"))
		{
			this.scope = PageContext.PAGE_SCOPE;
		}
	}

	public void setErrorId(final String errorId)
	{
		this.errorId = errorId;
	}

}
