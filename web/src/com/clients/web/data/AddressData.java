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


public class AddressData implements Serializable
{

	private String firstname;
	private String lastname;
	private String streetname;
	private String streetnumber;
	private String city;
	private String postalcode;

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

	public void setCity(final String city)
	{
		this.city = city;
	}

	public String getCity()
	{
		return city;
	}

	public String getPostalcode()
	{
		return postalcode;
	}

	public void setPostalcode(final String postalcode)
	{
		this.postalcode = postalcode;
	}



}
