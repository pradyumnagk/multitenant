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
package com.clients.web.controllers.forms.cart;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class CartValidator implements Validator
{

	protected final static Logger LOG = Logger.getLogger(CartValidator.class.getName());

	@Override
	public void validate(final Object target, final Errors errors)
	{
		final CartItemInfo cartInfo = (CartItemInfo) target;
		final String id = cartInfo.getId();
		if (cartInfo.isQuickAdd())
		{
			final String quantity = cartInfo.getQuantity();
			final String productCode = cartInfo.getProductCode();
			try
			{
				if (StringUtils.isEmpty(quantity) || !StringUtils.isNumeric(quantity) || Integer.parseInt(quantity) < 1)
				{
					errors.reject(id, "cart.quantity.invalid");
				}
			}
			catch (final NumberFormatException e)
			{
				errors.reject(id, "cart.quantity.invalid");
			}
			if (StringUtils.isEmpty(productCode))
			{
				errors.reject(id, "cart.productCode.invalid");
			}
		}
		else
		{
			final List<String> quantities = cartInfo.getQuantities();

			for (int i = 0; i < quantities.size(); i++)
			{
				final String q = quantities.get(i);
				try
				{
					if (StringUtils.isEmpty(q) || !StringUtils.isNumeric(q)
							|| Integer.parseInt(q) < 0)
					{
						errors.reject("entry_" + i, "cart.quantity.invalid");
					}
				}
				catch (final NumberFormatException e)
				{
					errors.reject("entry_" + i, "cart.quantity.invalid");
				}
			}
			final String p = cartInfo.getPlusQuantityEntry();

			if (p != null && (!StringUtils.isNumeric(p) || Integer.parseInt(p) < 0))
			{
				errors.reject("global", "cart.quantity.invalid");
			}
			final String m = cartInfo.getMinusQuantityEntry();
			if (m != null && (!StringUtils.isNumeric(m) || Integer.parseInt(m) < 0))
			{
				errors.reject("global", "cart.quantity.invalid");
			}
		}
	}

	@Override
	public boolean supports(final Class clazz)
	{
		return CartItemInfo.class.equals(clazz);
	}


}
