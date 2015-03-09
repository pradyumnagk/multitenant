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
package com.clients.web.converters;

import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import com.clients.web.data.AddressData;
import com.clients.web.data.UserData;
import de.hybris.platform.wishlist2.Wishlist2Service;
import de.hybris.platform.wishlist2.model.Wishlist2Model;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;


public class UserConverter implements Converter<UserModel, UserData>
{

	protected final static Logger LOG = Logger.getLogger(UserConverter.class.getName());

	protected Wishlist2Service wishlistService;
	protected WishlistConverter wishlistConverter;


	@Override
	public UserData convert(final UserModel source) throws ConversionException
	{
		return convert(source, new UserData());
	}

	@Override
	public UserData convert(final UserModel source, final UserData prototype) throws ConversionException
	{
		prototype.setLogin(source.getUid());
		final Collection<AddressModel> addresses = source.getAddresses();
		if (addresses == null || addresses.isEmpty())
		{
			LOG.warn("User: [" + source.getUid() + "] has no address!");
		}
		else
		{
			if (addresses.size() > 1)
			{
				LOG.warn("User: [" + source.getUid() + "] has more than one address. Taking first one!");
			}
			final AddressModel address = addresses.iterator().next();
			prototype.setMail(address.getEmail());
			final AddressData addressData = new AddressData();
			addressData.setFirstname(address.getFirstname());
			addressData.setLastname(address.getLastname());
			addressData.setStreetname(address.getStreetname());
			addressData.setStreetnumber(address.getStreetnumber());
			addressData.setPostalcode(address.getPostalcode());
			addressData.setCity(address.getTown());
			prototype.setAddressData(addressData);
		}

		final List<Wishlist2Model> wishlists = wishlistService.getWishlists(source);
		if (wishlists != null && !wishlists.isEmpty())
		{
			LOG.debug("Assigning " + wishlists.size() + " to user [" + source.getUid() + "]");
			prototype.setWishlists(wishlistConverter.convertAll(wishlists));
		}
		return prototype;
	}

	public void setWishlistService(final Wishlist2Service wishlistService)
	{
		this.wishlistService = wishlistService;
	}

	public void setWishlistConverter(final WishlistConverter wishlistConverter)
	{
		this.wishlistConverter = wishlistConverter;
	}

}
