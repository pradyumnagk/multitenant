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
package com.clients.web.mappings.preview.deeplink;

import de.hybris.platform.deeplink.model.rules.DeeplinkUrlModel;
import de.hybris.platform.deeplink.pojo.DeeplinkUrlInfo;
import de.hybris.platform.deeplink.services.DeeplinkUrlService;

import java.util.List;


/**
 * Holds values resolved from token by {@link PreviewMultitenantUrlResolver}.
 * <p/>
 * <b>Note:</b> <br/>
 * 
 * @see DeeplinkUrlInfo
 * @see DeeplinkUrlService#generateUrl(String)
 * @author karol.walczak
 * 
 */
public class PreviewDeeplinkInfo extends DeeplinkUrlInfo
{
	protected String labelOrId;
	protected String catalogId;
	protected List<String> categoryPathId;
	protected String productCode;

	public PreviewDeeplinkInfo(final DeeplinkUrlModel deeplinkUrl, final Object contextObject)
	{
		super(deeplinkUrl, contextObject);

	}

	/**
	 * Instantiates a new deeplink url info.
	 * 
	 * @param deeplinkUrl
	 *           the deeplink url
	 * @param contextObject
	 *           the context object
	 */
	public PreviewDeeplinkInfo(final DeeplinkUrlModel deeplinkUrl, final Object contextObject, final String catalogId,
			final List<String> categoryPathId, final String productCode)
	{
		super(deeplinkUrl, contextObject);
		this.catalogId = catalogId;
		this.categoryPathId = categoryPathId;
		this.productCode = productCode;
	}

	public String getCatalogId()
	{
		return catalogId;
	}

	public List<String> getCategoryPathId()
	{
		return categoryPathId;
	}

	public String getLabelOrId()
	{
		return labelOrId;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setCatalogId(final String catalogId)
	{
		this.catalogId = catalogId;
	}

	public void setCategoryPathId(final List<String> categoryPathId)
	{
		this.categoryPathId = categoryPathId;
	}

	public void setLabelOrId(final String labelOrId)
	{
		this.labelOrId = labelOrId;
	}

	public void setProductCode(final String productCode)
	{
		this.productCode = productCode;
	}

}
