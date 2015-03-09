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
package com.clients.web.facades;

import de.hybris.platform.order.InvalidCartException;
import com.clients.web.data.AddressData;
import com.clients.web.data.CartData;
import com.clients.web.data.DebitPaymentData;

import java.util.List;


public interface CartFacade
{

	CartData getCurrentCart();

	boolean calculateCart();

	boolean hasCart();

	void addToCart(String productCode, long quantity);

	void updateQuantities(List<Long> quantities);

	void updateQuantity(String productCode, long newQuantity);

	void deleteCartEntry(Integer cartEntryNumber);

	String placeOrder(AddressData deliveryAddress, DebitPaymentData payment) throws InvalidCartException;


}
