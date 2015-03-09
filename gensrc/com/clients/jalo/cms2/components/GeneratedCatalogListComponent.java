/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 16 Jan, 2015 12:50:02 AM                    ---
 * ----------------------------------------------------------------
 */
package com.clients.jalo.cms2.components;

import com.clients.constants.MultitenantConstants;
import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.List;

/**
 * Generated class for type {@link com.clients.jalo.cms2.components.CatalogListComponent CatalogListComponent1}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCatalogListComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>CatalogListComponent1.catalogs</code> attribute **/
	public static final String CATALOGS = "catalogs".intern();
	/** Qualifier of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute **/
	public static final String USECATALOGSOFSTORE = "useCatalogsOfStore".intern();
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n CATALOGS's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedCatalogListComponent> CATALOGSHANDLER = new BidirectionalOneToManyHandler<GeneratedCatalogListComponent>(
	MultitenantConstants.TC.CATALOGLISTCOMPONENT1,
	false,
	"catalogs".intern(),
	"catalogsPOS".intern(),
	true,
	true,
	CollectionType.COLLECTION
	);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogListComponent1.catalogs</code> attribute.
	 * @return the catalogs
	 */
	public List<Catalog> getCatalogs(final SessionContext ctx)
	{
		final List<Catalog> items = getLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogListComponent1.catalogs</code> attribute.
	 * @return the catalogs
	 */
	public List<Catalog> getCatalogs()
	{
		return getCatalogs( getSession().getSessionContext() );
	}
	
	public long getCatalogsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null
		);
	}
	
	public long getCatalogsCount()
	{
		return getCatalogsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogListComponent1.catalogs</code> attribute. 
	 * @param value the catalogs
	 */
	public void setCatalogs(final SessionContext ctx, final List<Catalog> value)
	{
		setLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogListComponent1.catalogs</code> attribute. 
	 * @param value the catalogs
	 */
	public void setCatalogs(final List<Catalog> value)
	{
		setCatalogs( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to catalogs. 
	 * @param value the item to add to catalogs
	 */
	public void addToCatalogs(final SessionContext ctx, final Catalog value)
	{
		addLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to catalogs. 
	 * @param value the item to add to catalogs
	 */
	public void addToCatalogs(final Catalog value)
	{
		addToCatalogs( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from catalogs. 
	 * @param value the item to remove from catalogs
	 */
	public void removeFromCatalogs(final SessionContext ctx, final Catalog value)
	{
		removeLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.CATALOGSFORCATALOGLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from catalogs. 
	 * @param value the item to remove from catalogs
	 */
	public void removeFromCatalogs(final Catalog value)
	{
		removeFromCatalogs( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute.
	 * @return the useCatalogsOfStore
	 */
	public Boolean isUseCatalogsOfStore(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, USECATALOGSOFSTORE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute.
	 * @return the useCatalogsOfStore
	 */
	public Boolean isUseCatalogsOfStore()
	{
		return isUseCatalogsOfStore( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute. 
	 * @return the useCatalogsOfStore
	 */
	public boolean isUseCatalogsOfStoreAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isUseCatalogsOfStore( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute. 
	 * @return the useCatalogsOfStore
	 */
	public boolean isUseCatalogsOfStoreAsPrimitive()
	{
		return isUseCatalogsOfStoreAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute. 
	 * @param value the useCatalogsOfStore
	 */
	public void setUseCatalogsOfStore(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, USECATALOGSOFSTORE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute. 
	 * @param value the useCatalogsOfStore
	 */
	public void setUseCatalogsOfStore(final Boolean value)
	{
		setUseCatalogsOfStore( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute. 
	 * @param value the useCatalogsOfStore
	 */
	public void setUseCatalogsOfStore(final SessionContext ctx, final boolean value)
	{
		setUseCatalogsOfStore( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogListComponent1.useCatalogsOfStore</code> attribute. 
	 * @param value the useCatalogsOfStore
	 */
	public void setUseCatalogsOfStore(final boolean value)
	{
		setUseCatalogsOfStore( getSession().getSessionContext(), value );
	}
	
}
