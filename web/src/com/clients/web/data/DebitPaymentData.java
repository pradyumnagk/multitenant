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


public class DebitPaymentData implements Serializable
{

	private String accountNumber;
	private String bank;
	private String bankIDNumber;
	private String baOwner;

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
