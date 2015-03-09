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

import javax.servlet.http.HttpServletRequest;


/**
 * Responsible for generating correct URL for given page.
 * <p/>
 * <b>Note:</b> <br/>
 * 
 * @author karol.walczak
 * 
 */
public interface URLMappingHandler
{
	/**
	 * Returns the relative URL for the specified page <code>page</code>.
	 * 
	 * @param httpRequest
	 *           HTTP request
	 * @param previewDataModel
	 *           current preview data
	 * @return relative URL for the specified page
	 */
	String getPageUrl(final HttpServletRequest httpRequest, final PreviewDataModel previewDataModel);

}
