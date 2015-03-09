/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 16 Jan, 2015 12:50:02 AM                    ---
 * ----------------------------------------------------------------
 */
package com.clients.jalo;

import com.clients.constants.MultitenantConstants;
import com.clients.jalo.cms2.components.CartAndWishlistComponent;
import com.clients.jalo.cms2.components.CartSummaryComponent;
import com.clients.jalo.cms2.components.CatalogListComponent;
import com.clients.jalo.cms2.components.CategoryTreeComponent;
import com.clients.jalo.cms2.components.LinkListComponent;
import com.clients.jalo.cms2.components.PromotionComponent;
import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.cms2.jalo.contents.components.CMSLinkComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.link.Link;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type <code>MultitenantManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedMultitenantManager extends Extension
{
	/**
	 * <i>Generated method</i> - Getter of the <code>Catalog.catalogListComponent1</code> attribute.
	 * @return the catalogListComponent1
	 */
	public Collection<CatalogListComponent> getCatalogListComponent1(final SessionContext ctx, final Catalog item)
	{
		final List<CatalogListComponent> items = item.getLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Catalog.catalogListComponent1</code> attribute.
	 * @return the catalogListComponent1
	 */
	public Collection<CatalogListComponent> getCatalogListComponent1(final Catalog item)
	{
		return getCatalogListComponent1( getSession().getSessionContext(), item );
	}
	
	public long getCatalogListComponent1Count(final SessionContext ctx, final Catalog item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null
		);
	}
	
	public long getCatalogListComponent1Count(final Catalog item)
	{
		return getCatalogListComponent1Count( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Catalog.catalogListComponent1</code> attribute. 
	 * @param value the catalogListComponent1
	 */
	public void setCatalogListComponent1(final SessionContext ctx, final Catalog item, final Collection<CatalogListComponent> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Catalog.catalogListComponent1</code> attribute. 
	 * @param value the catalogListComponent1
	 */
	public void setCatalogListComponent1(final Catalog item, final Collection<CatalogListComponent> value)
	{
		setCatalogListComponent1( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to catalogListComponent1. 
	 * @param value the item to add to catalogListComponent1
	 */
	public void addToCatalogListComponent1(final SessionContext ctx, final Catalog item, final CatalogListComponent value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to catalogListComponent1. 
	 * @param value the item to add to catalogListComponent1
	 */
	public void addToCatalogListComponent1(final Catalog item, final CatalogListComponent value)
	{
		addToCatalogListComponent1( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from catalogListComponent1. 
	 * @param value the item to remove from catalogListComponent1
	 */
	public void removeFromCatalogListComponent1(final SessionContext ctx, final Catalog item, final CatalogListComponent value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from catalogListComponent1. 
	 * @param value the item to remove from catalogListComponent1
	 */
	public void removeFromCatalogListComponent1(final Catalog item, final CatalogListComponent value)
	{
		removeFromCatalogListComponent1( getSession().getSessionContext(), item, value );
	}
	
	public CartAndWishlistComponent createCartAndWishlistComponent1(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( MultitenantConstants.TC.CARTANDWISHLISTCOMPONENT1 );
			return (CartAndWishlistComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CartAndWishlistComponent1 : "+e.getMessage(), 0 );
		}
	}
	
	public CartAndWishlistComponent createCartAndWishlistComponent1(final Map attributeValues)
	{
		return createCartAndWishlistComponent1( getSession().getSessionContext(), attributeValues );
	}
	
	public CartSummaryComponent createCartSummaryComponent1(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( MultitenantConstants.TC.CARTSUMMARYCOMPONENT1 );
			return (CartSummaryComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CartSummaryComponent1 : "+e.getMessage(), 0 );
		}
	}
	
	public CartSummaryComponent createCartSummaryComponent1(final Map attributeValues)
	{
		return createCartSummaryComponent1( getSession().getSessionContext(), attributeValues );
	}
	
	public CatalogListComponent createCatalogListComponent1(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( MultitenantConstants.TC.CATALOGLISTCOMPONENT1 );
			return (CatalogListComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CatalogListComponent1 : "+e.getMessage(), 0 );
		}
	}
	
	public CatalogListComponent createCatalogListComponent1(final Map attributeValues)
	{
		return createCatalogListComponent1( getSession().getSessionContext(), attributeValues );
	}
	
	public CategoryTreeComponent createCategoryTreeComponent1(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( MultitenantConstants.TC.CATEGORYTREECOMPONENT1 );
			return (CategoryTreeComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating CategoryTreeComponent1 : "+e.getMessage(), 0 );
		}
	}
	
	public CategoryTreeComponent createCategoryTreeComponent1(final Map attributeValues)
	{
		return createCategoryTreeComponent1( getSession().getSessionContext(), attributeValues );
	}
	
	public LinkListComponent createLinkListComponent1(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( MultitenantConstants.TC.LINKLISTCOMPONENT1 );
			return (LinkListComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating LinkListComponent1 : "+e.getMessage(), 0 );
		}
	}
	
	public LinkListComponent createLinkListComponent1(final Map attributeValues)
	{
		return createLinkListComponent1( getSession().getSessionContext(), attributeValues );
	}
	
	public PromotionComponent createPromotionComponent1(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( MultitenantConstants.TC.PROMOTIONCOMPONENT1 );
			return (PromotionComponent)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating PromotionComponent1 : "+e.getMessage(), 0 );
		}
	}
	
	public PromotionComponent createPromotionComponent1(final Map attributeValues)
	{
		return createPromotionComponent1( getSession().getSessionContext(), attributeValues );
	}
	
	@Override
	public String getName()
	{
		return MultitenantConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSLinkComponent.linkListComponent1</code> attribute.
	 * @return the linkListComponent1
	 */
	public Collection<LinkListComponent> getLinkListComponent1(final SessionContext ctx, final CMSLinkComponent item)
	{
		final List<LinkListComponent> items = item.getLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CMSLinkComponent.linkListComponent1</code> attribute.
	 * @return the linkListComponent1
	 */
	public Collection<LinkListComponent> getLinkListComponent1(final CMSLinkComponent item)
	{
		return getLinkListComponent1( getSession().getSessionContext(), item );
	}
	
	public long getLinkListComponent1Count(final SessionContext ctx, final CMSLinkComponent item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null
		);
	}
	
	public long getLinkListComponent1Count(final CMSLinkComponent item)
	{
		return getLinkListComponent1Count( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSLinkComponent.linkListComponent1</code> attribute. 
	 * @param value the linkListComponent1
	 */
	public void setLinkListComponent1(final SessionContext ctx, final CMSLinkComponent item, final Collection<LinkListComponent> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CMSLinkComponent.linkListComponent1</code> attribute. 
	 * @param value the linkListComponent1
	 */
	public void setLinkListComponent1(final CMSLinkComponent item, final Collection<LinkListComponent> value)
	{
		setLinkListComponent1( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to linkListComponent1. 
	 * @param value the item to add to linkListComponent1
	 */
	public void addToLinkListComponent1(final SessionContext ctx, final CMSLinkComponent item, final LinkListComponent value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to linkListComponent1. 
	 * @param value the item to add to linkListComponent1
	 */
	public void addToLinkListComponent1(final CMSLinkComponent item, final LinkListComponent value)
	{
		addToLinkListComponent1( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from linkListComponent1. 
	 * @param value the item to remove from linkListComponent1
	 */
	public void removeFromLinkListComponent1(final SessionContext ctx, final CMSLinkComponent item, final LinkListComponent value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from linkListComponent1. 
	 * @param value the item to remove from linkListComponent1
	 */
	public void removeFromLinkListComponent1(final CMSLinkComponent item, final LinkListComponent value)
	{
		removeFromLinkListComponent1( getSession().getSessionContext(), item, value );
	}
	
}
