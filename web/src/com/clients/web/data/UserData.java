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
package com.clients.web.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UserData implements Serializable
{

	private String login;
	private String mail;

	private AddressData addressData;
	private List<WishlistData> wishlists;

	public UserData()
	{
		this.wishlists = new ArrayList<WishlistData>();
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(final String login)
	{
		this.login = login;
	}


	public String getMail()
	{
		return mail;
	}

	public void setMail(final String mail)
	{
		this.mail = mail;
	}

	public void setAddressData(final AddressData addressData)
	{
		this.addressData = addressData;
	}

	public AddressData getAddressData()
	{
		return addressData;
	}


	public void setWishlists(final List<WishlistData> wishlists)
	{
		this.wishlists = wishlists;
	}

	public List<WishlistData> getWishlists()
	{
		return wishlists;
	}

}
