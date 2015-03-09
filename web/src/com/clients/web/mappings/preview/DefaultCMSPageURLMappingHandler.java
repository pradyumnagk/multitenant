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
package com.clients.web.mappings.preview;

import de.hybris.platform.cms2.model.preview.PreviewDataModel;
import de.hybris.platform.deeplink.services.DeeplinkUrlService;
import com.clients.web.servlets.util.FilterSpringUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * Responsible for generating correct URL for given page.
 * <p/>
 * <b>Note:</b> <br/>
 * For generate proper URL we using deeplinks mechanism. In order to get more information about that please see
 * {@link DeeplinkUrlService#generateUrl(String)}
 * 
 * @author karol.walczak
 * 
 */
public class DefaultCMSPageURLMappingHandler implements URLMappingHandler
{
	protected final static Logger LOG = Logger.getLogger(DefaultCMSPageURLMappingHandler.class);
	protected final static String MAPPING_URL_BEAN_NAME = "multitenantUrlMap";
	protected final static String PREVIEW_STORETEMPLATE_DEEPLINK_TOKEN = "preview_multitenant";

	/**
	 * Returns the relative URL for the specified page <code>page</code>.
	 * 
	 * @param httpRequest
	 *           HTTP request
	 * @return relative URL for the specified page
	 */
	@Override
	public String getPageUrl(final HttpServletRequest httpRequest, final PreviewDataModel previewDataModel)
	{
		final DeeplinkUrlService deeplinkUrlService = getDeeplinkUrlService(httpRequest);
		final StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(PREVIEW_STORETEMPLATE_DEEPLINK_TOKEN);

		if (previewDataModel != null)
		{
			tokenBuilder.append("-");
			tokenBuilder.append(previewDataModel.getPk());
		}
		return deeplinkUrlService.generateUrl(tokenBuilder.toString()).getUrl();
	}

	protected DeeplinkUrlService getDeeplinkUrlService(final HttpServletRequest request)
	{
		return FilterSpringUtil.getSpringBean(request, "deeplinkUrlService", DeeplinkUrlService.class);
	}
}
