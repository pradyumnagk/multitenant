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
package com.clients.web.controllers.forms.logout;

import de.hybris.platform.cms2.misc.CMSFilter;
import de.hybris.platform.cms2.model.preview.CMSPreviewTicketModel;
import de.hybris.platform.cms2.model.preview.PreviewDataModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPreviewService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.security.auth.AuthenticationService;
import de.hybris.platform.servicelayer.session.SessionService;
import com.clients.web.facades.UserFacade;
import com.clients.web.servlets.CMSSiteFilter;
import com.clients.web.servlets.util.FilterSpringUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * Simple controller that is responsible for user logs out
 * <p/>
 * <b>Note:</b> <br/>
 * In method that process request we trying match <b><code>proceedLogout</code></b>parameter. When we are success that
 * means - user clicked <b><code>Logout</code></b> button and we just invalidate current session.
 * 
 * @see AuthenticationService#logout()
 * 
 * @author karol.walczak
 * 
 */
public class LogoutController implements Controller
{
	protected final static Logger LOG = Logger.getLogger(LogoutController.class);
	protected final static String LOGGED_IN = "loggedIn";
	protected final static String LOGOUT_TOKEN = "proceedLogout";

	private UserFacade userFacade;
	private String view;
	private SessionService sessionService;
	private CMSPreviewService cmsPreviewService;
	private ModelService modelService;

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public CMSPreviewService getCmsPreviewService()
	{
		return cmsPreviewService;
	}

	public void setCmsPreviewService(final CMSPreviewService cmsPreviewService)
	{
		this.cmsPreviewService = cmsPreviewService;
	}

	public SessionService getSessionService()
	{
		return sessionService;
	}

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	public void setUserFacade(final UserFacade userFacade)
	{
		this.userFacade = userFacade;
	}

	public void setView(final String view)
	{
		this.view = view;
	}

	public UserFacade getUserFacade()
	{
		return userFacade;
	}

	public String getView()
	{
		return view;
	}


	@Override
	public ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws Exception //NOPMD: spring MVC specific
	{
		final String logoutToken = request.getParameter(LOGOUT_TOKEN);
		if (StringUtils.isNotBlank(logoutToken) && Boolean.TRUE.equals(Boolean.valueOf(logoutToken)))
		{
			final StringBuilder redirectUrl = new StringBuilder(request.getContextPath());
			final String ticketId = getPreviewTicketId(request);
			final PreviewDataModel previewData = getPreviewData(ticketId);
			if (previewData != null)
			{
				redirectUrl.append("/" + CMSSiteFilter.PREVIEW_TOKEN);
				redirectUrl.append("?");
				redirectUrl.append(CMSSiteFilter.PREVIEW_TICKET_ID_PARAM);
				redirectUrl.append("=");
				redirectUrl.append(ticketId);
				previewData.setUser(null);
				getModelService().save(previewData);
			}
			logout(request);
			response.sendRedirect(redirectUrl.toString());
			return null;
		}
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put(LOGGED_IN, Boolean.valueOf(userFacade.isLoggedIn()));
		return new ModelAndView(view, model);
	}

	/**
	 * Logs out current user
	 * 
	 * @param httpRequest
	 *           - current request
	 */
	protected void logout(final HttpServletRequest httpRequest)
	{
		getSpringBean(httpRequest, "authenticationService", AuthenticationService.class).logout();

		if (LOG.isDebugEnabled())
		{
			LOG.debug("User was successfully loged out!");
		}
	}

	/**
	 * Retrieves {@link CMSFilter#PREVIEW_TICKET_ID_PARAM} from current request
	 * 
	 * @param httpRequest
	 *           current request
	 * @return current ticket id
	 */
	protected String getPreviewTicketId(final HttpServletRequest httpRequest)
	{
		String id = httpRequest.getParameter(CMSSiteFilter.PREVIEW_TICKET_ID_PARAM);
		if (StringUtils.isBlank(id))
		{
			id = getSessionService().getAttribute(CMSSiteFilter.PREVIEW_TICKET_ID_PARAM);
		}
		return id;
	}

	/**
	 * Retrieves current Preview Data according to given ticked id
	 * 
	 * @param ticketId
	 *           current ticket id
	 * @return current Preview Data attached to given ticket if any otherwise null
	 */
	protected PreviewDataModel getPreviewData(final String ticketId)
	{
		PreviewDataModel ret = null;
		final CMSPreviewTicketModel previewTicket = getCmsPreviewService().getPreviewTicket(ticketId);
		if (previewTicket != null)
		{
			ret = previewTicket.getPreviewData();
		}
		return ret;
	}

	/**
	 * Returns the Spring bean with name <code>beanName</code> and of type <code>beanClass</code>. If no bean could be
	 * resolved for the specified name, the bean is looked up using type.
	 * 
	 * @param <T>
	 *           type of the bean
	 * @param httpRequest
	 *           the HTTP request
	 * @param beanName
	 *           name of the bean or <code>null</code> if it should be automatically resolved using type
	 * @param beanClass
	 *           expected type of the bean
	 * @return the bean matching the given arguments or <code>null</code> if no bean could be resolved
	 * 
	 */
	protected <T> T getSpringBean(final HttpServletRequest httpRequest, final String beanName, final Class<T> beanClass)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, beanName, beanClass);
	}
}
