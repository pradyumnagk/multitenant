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
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.cms2.constants.Cms2Constants;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.misc.CMSFilter;
import de.hybris.platform.cms2.model.preview.PreviewDataModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPreviewService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.cms2.servicelayer.services.admin.CMSAdminSiteService;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import com.clients.web.servlets.util.FilterSpringUtil;
import de.hybris.platform.util.Utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * Default context information loader
 * 
 * @author karol.walczak
 * 
 */
public class ContextInformationLoader
{
	private final static Logger LOG = Logger.getLogger(ContextInformationLoader.class);
	private static Object lock = new Object();

	private static ContextInformationLoader instance = null;

	public static ContextInformationLoader getInstance()
	{
		synchronized (lock)
		{
			if (instance == null)
			{
				instance = new ContextInformationLoader();
			}
		}
		return instance;
	}

	public CMSSiteModel getCurrentSite(final HttpServletRequest httpRequest)
	{

		return getCMSSiteService(httpRequest).getCurrentSite();
	}

	public void setCatalogVersions(final HttpServletRequest httpRequest)
	{
		try
		{
			final CMSSiteModel currentSiteModel = getCurrentSite(httpRequest);
			if (currentSiteModel != null)
			{
				getCMSSiteService(httpRequest).setCurrentSiteAndCatalogVersions(currentSiteModel, true);
			}
		}
		catch (final CMSItemNotFoundException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Catalog has no active catalog version!", e);
			}
		}
	}

	public void setCurrentSite(final HttpServletRequest httpRequest, final String absoluteURL)
	{
		try
		{
			final URL currentURL = new URL(absoluteURL);
			final CMSSiteService cmsSiteService = getCMSSiteService(httpRequest);
			final CMSSiteModel cmsSiteModel = cmsSiteService.getSiteForURL(currentURL);
			if (cmsSiteModel != null)
			{
				cmsSiteService.setCurrentSite(cmsSiteModel);
			}
		}
		catch (final MalformedURLException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Cannot find CMSSite asociated with current URL ( " + absoluteURL
						+ " - check whether this is correct URL) !");
			}
		}
		catch (final CMSItemNotFoundException e)
		{
			LOG.warn("Cannot find CMSSite asociated with current URL (" + absoluteURL + ")!");
		}
	}

	public void loadCatalogVersions(final HttpServletRequest httpRequest, final Collection<CatalogVersionModel> catalogVersions)
	{
		getCatalogService(httpRequest).setSessionCatalogVersions(new HashSet<CatalogVersionModel>(catalogVersions));
	}

	public void initialize(final HttpServletRequest httpRequest, final PreviewDataModel previewDataModel)
	{
		loadActiveCmsSite(httpRequest, previewDataModel);
		loadCatalogVersions(httpRequest, previewDataModel.getCatalogVersions());
		loadCurrentCatalogVersion(httpRequest);
	}

	public void loadCurrentCatalogVersion(final HttpServletRequest httpRequest)
	{
		final CMSSiteService cmsSiteService = getCMSSiteService(httpRequest);
		final CMSSiteModel currentSiteModel = cmsSiteService.getCurrentSite();
		if (currentSiteModel != null)
		{
			try
			{
				cmsSiteService.setCurrentCatalogVersion(currentSiteModel.getDefaultCatalog().getActiveCatalogVersion());
			}
			catch (final CMSItemNotFoundException e)
			{
				LOG.warn("Could not set current catalog version.", e);
			}
		}
		else
		{
			LOG.warn("Could not ind current CMS Site!.");
		}
	}

	public void loadActiveCmsSite(final HttpServletRequest httpRequest, final PreviewDataModel previewDataModel)
	{
		final CMSSiteService cmsSiteService = getCMSSiteService(httpRequest);
		if (previewDataModel.getActiveSite() == null)
		{
			LOG.warn("Could not set active site. Reason: No active site was selected!");
		}
		else
		{
			cmsSiteService.setCurrentSite(previewDataModel.getActiveSite());
		}
	}

	protected void loadFakeUser(final HttpServletRequest httpRequest, final UserModel fakeUser)
	{
		final UserService userService = getUserService(httpRequest);
		final UserModel currentUser = userService.getCurrentUser();
		if (fakeUser != null && !fakeUser.equals(currentUser))
		{
			userService.setCurrentUser(fakeUser);
		}
	}

	protected void loadFakeUserGroup(final HttpServletRequest httpRequest, final PreviewDataModel preivewDataModel)
	{
		if (preivewDataModel.getUser() == null && preivewDataModel.getUserGroup() != null)
		{
			UserModel userWithinDesiredGroup = null;
			final UserGroupModel fakeUserGroup = preivewDataModel.getUserGroup();
			for (final PrincipalModel princicpalModel : fakeUserGroup.getMembers())
			{
				if (princicpalModel instanceof UserModel)
				{
					userWithinDesiredGroup = (UserModel) princicpalModel;
					break;
				}
			}
			if (userWithinDesiredGroup != null)
			{
				loadFakeUser(httpRequest, userWithinDesiredGroup);
			}
		}
	}

	protected void loadFakeLanguage(final HttpServletRequest httpRequest, final LanguageModel languageModel)
	{

		if (languageModel != null)
		{
			final String[] codeParts = Utilities.parseLocaleCodes(languageModel.getIsocode());
			getI18NService(httpRequest).setCurrentLocale(new Locale(codeParts[0], codeParts[1], codeParts[2]));
		}
	}

	protected void storePreviewTicketIDWithinSession(final HttpServletRequest httpRequest)
	{
		final String ticketId = httpRequest.getParameter(CMSFilter.PREVIEW_TICKET_ID_PARAM);
		if (StringUtils.isNotBlank(ticketId))
		{
			JaloSession.getCurrentSession().setAttribute(CMSFilter.PREVIEW_TICKET_ID_PARAM, ticketId);
		}
	}

	protected void loadFakeDate(final Date fakeDate)
	{
		if (fakeDate != null)
		{
			JaloSession.getCurrentSession().setAttribute(Cms2Constants.PREVIEW_TIME, fakeDate);
		}
	}

	public void loadFakeContextInformation(final HttpServletRequest httpRequest, final PreviewDataModel previewData)
	{
		//set fake user
		loadFakeUser(httpRequest, previewData.getUser());
		//set fake user group
		loadFakeUserGroup(httpRequest, previewData);
		//set fake language
		loadFakeLanguage(httpRequest, previewData.getLanguage());
		//set fake date
		loadFakeDate(previewData.getTime());

		storePreviewTicketIDWithinSession(httpRequest);
	}

	public void storePreviewData(final HttpServletRequest httpRequest, final PreviewDataModel previewData)
	{
		final ModelService modelService = getModelService(httpRequest);
		if (previewData == null)
		{
			LOG.warn("Could not store preview data. Reason: Preview data was null.");
		}
		else
		{
			if (modelService == null)
			{
				LOG.warn("Could not store preview data. Reason: Model service was null.");
			}
			else
			{
				modelService.save(previewData);
			}
		}
	}

	protected CMSSiteService getCMSSiteService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "cmsSiteService", CMSSiteService.class);
	}

	protected CMSPreviewService getCMSPreviewService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "cmsPreviewService", CMSPreviewService.class);
	}

	protected CatalogService getCatalogService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "catalogService", CatalogService.class);
	}

	protected UserService getUserService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "userService", UserService.class);
	}

	protected I18NService getI18NService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "i18nService", I18NService.class);
	}

	protected ModelService getModelService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "modelService", ModelService.class);
	}

	protected CMSAdminSiteService getCMSAdminSiteService(final HttpServletRequest httpRequest)
	{
		return FilterSpringUtil.getSpringBean(httpRequest, "cmsAdminSiteService", CMSAdminSiteService.class);
	}
}
