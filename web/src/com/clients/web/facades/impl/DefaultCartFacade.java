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
package com.clients.web.facades.impl;

import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.DebitPaymentInfoModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.order.OrderService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;
import com.clients.web.converters.CartConverter;
import com.clients.web.data.AddressData;
import com.clients.web.data.CartData;
import com.clients.web.data.DebitPaymentData;
import com.clients.web.facades.CartFacade;
import de.hybris.platform.variants.jalo.VariantProduct;
import de.hybris.platform.variants.jalo.VariantsManager;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;


public class DefaultCartFacade implements CartFacade
{
	final static private Logger LOG = Logger.getLogger(DefaultCartFacade.class.getName());

	protected CartService cartService;
	protected OrderService orderService;
	protected ProductService productService;
	protected CartConverter cartConverter;
	protected CMSSiteService cmsSiteService;
	protected ModelService modelService;

	@Override
	public void addToCart(final String productCode, final long quantity)
	{
		if (quantity > 0)
		{
			final ProductModel product = chooseProduct(productService.getProduct(productCode));

			try
			{
				cartService.addToCart(getCart(), product, quantity, null);
			}
			catch (final InvalidCartException e)
			{
				LOG.warn("Could not add product with code [" + productCode + "] to cart: " + e.getMessage());
			}
		}
	}

	@Override
	public boolean calculateCart()
	{
		return cartService.calculateCart(cartService.getSessionCart());
	}

	@Override
	public void deleteCartEntry(final Integer cartEntryNumber)
	{
		final CartModel cart = getCart();
		AbstractOrderEntryModel e = null;
		for (final AbstractOrderEntryModel entry : cart.getEntries())
		{
			if (entry.getEntryNumber().equals(cartEntryNumber))
			{
				e = entry;
				break;
			}
		}
		if (e != null)
		{
			modelService.remove(e);
			calculateCart();
		}
	}

	@Override
	public CartData getCurrentCart()
	{
		return (cartService.hasCart()) ? cartConverter.convert(cartService.getSessionCart()) : new CartData();
	}

	@Override
	public boolean hasCart()
	{
		return cartService.hasCart();
	}

	@Override
	public String placeOrder(final AddressData deliveryAddress, final DebitPaymentData payment) throws InvalidCartException
	{
		final AddressModel delivery = new AddressModel();
		delivery.setFirstname(deliveryAddress.getFirstname());
		delivery.setLastname(deliveryAddress.getLastname());
		delivery.setStreetname(deliveryAddress.getStreetname());
		delivery.setStreetnumber(deliveryAddress.getStreetnumber());
		delivery.setTown(deliveryAddress.getCity());
		delivery.setPostalcode(deliveryAddress.getPostalcode());

		final DebitPaymentInfoModel debit = new DebitPaymentInfoModel();
		debit.setAccountNumber(payment.getAccountNumber());
		debit.setBank(payment.getBank());
		debit.setBankIDNumber(payment.getBankIDNumber());
		debit.setBaOwner(payment.getBaOwner());

		final CartModel cart = cartService.getSessionCart();

		final OrderModel order = orderService.placeOrder(cart, delivery, null, debit);
		return order.getCode();
	}

	public void setCartConverter(final CartConverter cartConverter)
	{
		this.cartConverter = cartConverter;
	}

	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}

	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	public void setOrderService(final OrderService orderService)
	{
		this.orderService = orderService;
	}

	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}

	@Override
	public void updateQuantities(final List<Long> quantities)
	{
		final CartModel cart = getCart();
		cartService.updateQuantities(cart, quantities);
		calculateCart();
	}

	@Override
	public void updateQuantity(final String productCode, final long newQuantity)
	{
		if (newQuantity >= 0)
		{
			final CartModel cart = getCart();
			final Long qty = Long.valueOf(newQuantity);
			for (final AbstractOrderEntryModel entry : cart.getEntries())
			{
				final ProductModel p = entry.getProduct();
				if (p.getCode().equals(productCode))
				{
					if (newQuantity <= 0)
					{
						modelService.remove(entry);
					}
					else
					{
						entry.setQuantity(qty);
						modelService.save(entry);
					}
					modelService.refresh(cart);
					calculateCart();
					return;
				}
			}
		}
	}

	/**
	 * Choose product. This method is temporary fix for issue: STOT-105 and should be removed after problem will be
	 * solved in better way.
	 * 
	 * @param product
	 *           model to check the product
	 * @return the choosen product model
	 */
	private ProductModel chooseProduct(final ProductModel product)
	{
		final Product productItem = modelService.getSource(product);
		if (VariantsManager.getInstance().isBaseProduct(productItem))
		{
			final Collection<VariantProduct> variants = VariantsManager.getInstance().getVariants(productItem);
			if (!variants.isEmpty())
			{
				return modelService.toModelLayer(variants.iterator().next());
			}
		}

		return product;
	}

	private CartModel getCart()
	{
		return cartService.getSessionCart();
	}
}
