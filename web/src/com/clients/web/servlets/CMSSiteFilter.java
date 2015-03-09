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
package com.clients.web.servlets;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.cms2.misc.CMSFilter;
import de.hybris.platform.cms2.misc.UrlUtils;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.preview.CMSPreviewTicketModel;
import de.hybris.platform.cms2.model.preview.PreviewDataModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPreviewService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.servicelayer.model.AbstractItemModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import com.clients.web.mappings.preview.DefaultCMSPageURLMappingHandler;
import com.clients.web.mappings.preview.URLMappingHandler;
import com.clients.web.servlets.util.FilterSpringUtil;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * Responsible for setting up application - to main responsibility belongs:
 * <p>
 * <ul>
 * <li>Matches current site by current URL</li>
 * <li>Setting current site in session (through {@link CMSSiteService} )</li>
 * <li>Setting current catalog version (through {@link CMSSiteService} )</li>
 * <li>Setting session catalog versions (through {@link CatalogService} )</li>
 * </ul>
 * </p>
 * <br/>
 * <b>Note</b>: In former versions (i.e. 4.1.1 and earlier) as a preview mechanism we used
 * {@link de.hybris.platform.cms2.misc.AbstractPreviewServlet} which actually is obsolete. All necessary logic was
 * adapted and moved here.
 * 
 * @author karol.walczak
 * 
 */
public class CMSSiteFilter extends AbstractOncePerUserRequestFilter implements CMSFilter
{
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger(CMSSiteFilter.class);


	@Override
	protected void doFilterInternal(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse,
			final FilterChain filterChain) throws ServletException, IOException
	{
		final String requestURL = httpRequest.getRequestURL().toString();
		final ContextInformationLoader informationLoader = ContextInformationLoader.getInstance();
		//check whether exits valid preview data
		if (!isPreviewDataModelValid(httpRequest))
		{
			//process normal request (i.e. normal browser non-cmscockpit request)
			proccessNormalRequest(httpRequest, informationLoader);
			//proceed filters
			filterChain.doFilter(httpRequest, httpResponse);
		}
		else if (StringUtils.contains(requestURL, PREVIEW_TOKEN))
		{
			final String redirectURL = processPreviewRequest(httpRequest, informationLoader);
			//redirect to computed URL
			httpResponse.sendRedirect(httpResponse.encodeRedirectURL(redirectURL));
			//next filter in chain won't be invoked!!!
		}
		else
		{
			//proceed filters
			filterChain.doFilter(httpRequest, httpResponse);
		}
	}

	/**
	 * Processing normal request (i.e. when user goes directly to that application - not from cmscockpit)
	 * <p/>
	 * <b>Note:</b> <br/>
	 * We preparing application by setting correct:
	 * <ul>
	 * <li>Current Site</li>
	 * <li>Current Catalog Versions</li>
	 * <li>Enabled language fallback</li>
	 * </ul>
	 * 
	 * @see ContextInformationLoader#setCurrentSite(HttpServletRequest,String)
	 * @see ContextInformationLoader#setCatalogVersions(HttpServletRequest )
	 * @param httpRequest
	 *           current request
	 * @param informationLoader
	 *           default context information loader
	 */
	protected void proccessNormalRequest(final HttpServletRequest httpRequest, final ContextInformationLoader informationLoader)
	{

		final String queryString = httpRequest.getQueryString();
		final String currentRequestURL = httpRequest.getRequestURL().toString();

		//set current site
		final CMSSiteModel cmsSiteModel = getCurrentCmsSite(httpRequest);
		if (cmsSiteModel == null || StringUtils.contains(queryString, CLEAR_CMSSITE_PARAM))
		{
			final String absoluteURL = StringUtils.removeEnd(currentRequestURL, "/")
					+ (StringUtils.isBlank(queryString) ? "" : "?" + queryString);
			informationLoader.setCurrentSite(httpRequest, absoluteURL);
		}

		informationLoader.setCatalogVersions(httpRequest);
		//set fall back language enabled
		setFallbackLanguage(httpRequest, Boolean.TRUE);
	}

	/**
	 * Processing preview request (i.e. request with additional parameters like {@link CMSFilter#PREVIEW_TOKEN} requested
	 * from cmscockpit) )
	 * <p/>
	 * <b>Note:</b> Processing preview data in order to generate target URL, and load necessary information in user
	 * session
	 * <ul>
	 * <li>Initialize information (Active CMSSite, Catalog versions,Current catalog version ) information getting from
	 * valid Preview Data</li>
	 * <li>Load all fake information (like: User, User group, Language, Time ...)
	 * <li>Generating target URL according to Preview Data
	 * </ul>
	 * 
	 * @param httpRequest
	 *           current request
	 * @param informationLoader
	 *           default context information loader
	 * 
	 * @return target URL
	 */
	protected String processPreviewRequest(final HttpServletRequest httpRequest, final ContextInformationLoader informationLoader)
	{
		String destionationURL = StringUtils.EMPTY;
		final PreviewDataModel previewDataModel = getPreviewData(getPreviewTicketId(httpRequest), httpRequest);

		//load necessary information 
		informationLoader.initialize(httpRequest, previewDataModel);
		//load fake context information
		informationLoader.loadFakeContextInformation(httpRequest, previewDataModel);
		//generate destination URL
		destionationURL = generatePreviewUrl(httpRequest, previewDataModel);

		// persist changes
		if (previewDataModel != null)
		{
			previewDataModel.setResourcePath(destionationURL);
			informationLoader.storePreviewData(httpRequest, previewDataModel);
		}

		final CMSPreviewTicketModel ticket = getCmsPreviewService(httpRequest).createPreviewTicket(previewDataModel);
		String parameterDelimiter = "?";
		if (StringUtils.contains(destionationURL, "?"))
		{
			parameterDelimiter = "&";
		}
		return destionationURL + parameterDelimiter + PREVIEW_TICKET_ID_PARAM + "=" + ticket.getId();

	}

	/**
	 * Enables or disables language fall back
	 * <p/>
	 * 
	 * @param httpRequest
	 *           current request
	 * @param enabled
	 *           enabled or disabled
	 */
	protected void setFallbackLanguage(final HttpServletRequest httpRequest, final Boolean enabled)
	{
		final SessionService sessionService = getSessionService(httpRequest);
		if (sessionService != null)
		{
			sessionService.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, enabled);
			sessionService.setAttribute(AbstractItemModel.LANGUAGE_FALLBACK_ENABLED_SERVICE_LAYER, enabled);
		}
	}

	/**
	 * Generates target URL accordingly to valid Preview Data passed as a parameter
	 * <p/>
	 * 
	 * @param httpRequest
	 *           current request
	 * @param previewDataModel
	 *           valid data model contains all necessary information
	 * @return target URL
	 */
	protected String generatePreviewUrl(final HttpServletRequest httpRequest, final PreviewDataModel previewDataModel)
	{
		String generatedPreviewUrl = StringUtils.EMPTY;
		if (previewDataModel != null)
		{

			if (StringUtils.isBlank(generatedPreviewUrl))
			{
				final AbstractPageModel abstractPageModel = previewDataModel.getPage();
				if (abstractPageModel != null)
				{
					generatedPreviewUrl = getURLMappingHanlder(httpRequest).getPageUrl(httpRequest, previewDataModel);
				}
				else
				{

					generatedPreviewUrl = previewDataModel.getResourcePath();
				}

			}
		}
		if (StringUtils.isBlank(generatedPreviewUrl))
		{
			generatedPreviewUrl = UrlUtils.extractHostInformationFromRequest(httpRequest, getCMSSiteService(httpRequest)
					.getCurrentSite());
		}
		generatedPreviewUrl = StringUtils.removeStart(generatedPreviewUrl, "/");
		return generatedPreviewUrl;
	}

	/**
	 * Retrieves current mapping handler in order to generate proper target URL for CMS Page
	 * <p/>
	 * 
	 * @param httpRequest
	 *           current request
	 * @return current mapping handler
	 */
	protected URLMappingHandler getURLMappingHanlder(final HttpServletRequest httpRequest)
	{
		URLMappingHandler urlMappingHandler = FilterSpringUtil.getSpringBean(httpRequest, "cmsPageUrlHndlerMapping",
				URLMappingHandler.class);
		if (urlMappingHandler == null)
		{
			urlMappingHandler = new DefaultCMSPageURLMappingHandler();
		}
		return urlMappingHandler;
	}


	@Override
	protected String getLogedUserId(final HttpServletRequest httpRequest)
	{
		String ret = StringUtils.EMPTY;
		final UserService userService = getUserService(httpRequest);
		final UserModel currentUser = userService.getCurrentUser();
		if (currentUser != null)
		{

			ret = currentUser.getUid();
		}
		return ret;
	}

	protected CMSSiteService getCMSSiteService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "cmsSiteService", CMSSiteService.class);
	}

	protected CMSPreviewService getCMSPreviewService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "cmsPreviewService", CMSPreviewService.class);
	}

	protected UserService getUserService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "userService", UserService.class);
	}

	protected CatalogService getCatalogService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "catalogService", CatalogService.class);
	}

	protected ModelService getModelService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "i18nservice", ModelService.class);
	}

	protected SessionService getSessionService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "sessionService", SessionService.class);
	}

	protected CMSPreviewService getCmsPreviewService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "cmsPreviewService", CMSPreviewService.class);
	}

	protected CMSSiteModel getCurrentCmsSite(final HttpServletRequest httpRequest)
	{
		return getCMSSiteService(httpRequest).getCurrentSite();
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
		String id = httpRequest.getParameter(PREVIEW_TICKET_ID_PARAM);
		if (StringUtils.isBlank(id))
		{
			id = getSessionService(httpRequest).getAttribute(PREVIEW_TICKET_ID_PARAM);
		}
		return id;
	}

	/**
	 * Checks whether current Preview Data is valid (not removed)
	 * 
	 * @param httpRequest
	 *           current request
	 * @return true whether is valid otherwise false
	 */
	protected boolean isPreviewDataModelValid(final HttpServletRequest httpRequest)
	{
		return getPreviewData(getPreviewTicketId(httpRequest), httpRequest) != null;
	}

	/**
	 * Retrieves current Preview Data according to given ticked id
	 * 
	 * @param ticketId
	 *           current ticket id
	 * @param httpRequest
	 *           current request
	 * @return current Preview Data attached to given ticket if any otherwise null
	 */
	protected PreviewDataModel getPreviewData(final String ticketId, final HttpServletRequest httpRequest)
	{
		PreviewDataModel ret = null;
		final CMSPreviewTicketModel previewTicket = getCmsPreviewService(httpRequest).getPreviewTicket(ticketId);
		if (previewTicket != null)
		{
			ret = previewTicket.getPreviewData();
		}
		return ret;
	}


}
