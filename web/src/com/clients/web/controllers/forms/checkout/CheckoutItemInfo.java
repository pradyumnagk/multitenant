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
package com.clients.web.controllers.forms.checkout;

import java.io.Serializable;


public class CheckoutItemInfo implements Serializable
{

	private String login;
	private String password;

	private String firstname;
	private String lastname;
	private String streetname;
	private String streetnumber;
	private String postalcode;
	private String city;

	private String accountNumber;
	private String bank;
	private String bankIDNumber;
	private String baOwner;

	public String getLogin()
	{
		return login;
	}

	public void setLogin(final String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(final String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(final String lastname)
	{
		this.lastname = lastname;
	}

	public String getStreetname()
	{
		return streetname;
	}

	public void setStreetname(final String streetname)
	{
		this.streetname = streetname;
	}

	public String getStreetnumber()
	{
		return streetnumber;
	}

	public void setStreetnumber(final String streetnumber)
	{
		this.streetnumber = streetnumber;
	}

	public String getPostalcode()
	{
		return postalcode;
	}

	public void setPostalcode(final String postalcode)
	{
		this.postalcode = postalcode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(final String city)
	{
		this.city = city;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(final String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getBank()
	{
		return bank;
	}

	public void setBank(final String bank)
	{
		this.bank = bank;
	}

	public String getBankIDNumber()
	{
		return bankIDNumber;
	}

	public void setBankIDNumber(final String bankIDNumber)
	{
		this.bankIDNumber = bankIDNumber;
	}

	public String getBaOwner()
	{
		return baOwner;
	}

	public void setBaOwner(final String baOwner)
	{
		this.baOwner = baOwner;
	}

}
