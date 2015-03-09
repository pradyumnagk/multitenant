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
package com.clients.jalo.cms2.components;

import de.hybris.platform.jalo.SessionContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class CartSummaryComponent extends GeneratedCartSummaryComponent
{
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(CartSummaryComponent.class.getName());


	@Override
	public Boolean isEnabled(final SessionContext ctx)
	{

		final boolean ret = StringUtils.isBlank(getSuplementaryText()) || StringUtils.isBlank(getCartTitle())
				|| getCartImage() != null && StringUtils.isNotBlank(getCartImage().getDownloadURL());
		return Boolean.valueOf(ret);
	}
}
