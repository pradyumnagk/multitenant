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
package com.clients.web.controllers.forms.registration;

public class RegistrationInfo
{

	private String login;
	private String password;
	private String password2;
	private String firstname;
	private String lastname;
	private String company;
	private String street;
	private String streetnumber;
	private String zip;
	private String city;
	private String mail;
	private String country;

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

	public String getPassword2()
	{
		return password2;
	}

	public void setPassword2(final String password2)
	{
		this.password2 = password2;
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

	public String getCompany()
	{
		return company;
	}

	public void setCompany(final String company)
	{
		this.company = company;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(final String street)
	{
		this.street = street;
	}

	public String getStreetnumber()
	{
		return streetnumber;
	}

	public void setStreetnumber(final String streetnumber)
	{
		this.streetnumber = streetnumber;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(final String zip)
	{
		this.zip = zip;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(final String city)
	{
		this.city = city;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(final String mail)
	{
		this.mail = mail;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(final String country)
	{
		this.country = country;
	}

}
