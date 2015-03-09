/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 16 Jan, 2015 12:50:02 AM                    ---
 * ----------------------------------------------------------------
 */
package com.clients.jalo.cms2.components;

import com.clients.constants.MultitenantConstants;
import de.hybris.platform.cms2.jalo.contents.components.CMSLinkComponent;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.BidirectionalOneToManyHandler;
import java.util.Collections;
import java.util.List;

/**
 * Generated class for type {@link com.clients.jalo.cms2.components.LinkListComponent LinkListComponent1}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedLinkListComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>LinkListComponent1.links</code> attribute **/
	public static final String LINKS = "links".intern();
	/** Qualifier of the <code>LinkListComponent1.columns</code> attribute **/
	public static final String COLUMNS = "columns".intern();
	/**
	* {@link BidirectionalOneToManyHandler} for handling 1:n LINKS's relation attributes from 'one' side.
	**/
	protected static final BidirectionalOneToManyHandler<GeneratedLinkListComponent> LINKSHANDLER = new BidirectionalOneToManyHandler<GeneratedLinkListComponent>(
	MultitenantConstants.TC.LINKLISTCOMPONENT1,
	false,
	"links".intern(),
	"linksPOS".intern(),
	true,
	true,
	CollectionType.COLLECTION
	);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkListComponent1.columns</code> attribute.
	 * @return the columns
	 */
	public Integer getColumns(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, COLUMNS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkListComponent1.columns</code> attribute.
	 * @return the columns
	 */
	public Integer getColumns()
	{
		return getColumns( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkListComponent1.columns</code> attribute. 
	 * @return the columns
	 */
	public int getColumnsAsPrimitive(final SessionContext ctx)
	{
		Integer value = getColumns( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkListComponent1.columns</code> attribute. 
	 * @return the columns
	 */
	public int getColumnsAsPrimitive()
	{
		return getColumnsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkListComponent1.columns</code> attribute. 
	 * @param value the columns
	 */
	public void setColumns(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, COLUMNS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkListComponent1.columns</code> attribute. 
	 * @param value the columns
	 */
	public void setColumns(final Integer value)
	{
		setColumns( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkListComponent1.columns</code> attribute. 
	 * @param value the columns
	 */
	public void setColumns(final SessionContext ctx, final int value)
	{
		setColumns( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkListComponent1.columns</code> attribute. 
	 * @param value the columns
	 */
	public void setColumns(final int value)
	{
		setColumns( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkListComponent1.links</code> attribute.
	 * @return the links
	 */
	public List<CMSLinkComponent> getLinks(final SessionContext ctx)
	{
		final List<CMSLinkComponent> items = getLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>LinkListComponent1.links</code> attribute.
	 * @return the links
	 */
	public List<CMSLinkComponent> getLinks()
	{
		return getLinks( getSession().getSessionContext() );
	}
	
	public long getLinksCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null
		);
	}
	
	public long getLinksCount()
	{
		return getLinksCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkListComponent1.links</code> attribute. 
	 * @param value the links
	 */
	public void setLinks(final SessionContext ctx, final List<CMSLinkComponent> value)
	{
		setLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>LinkListComponent1.links</code> attribute. 
	 * @param value the links
	 */
	public void setLinks(final List<CMSLinkComponent> value)
	{
		setLinks( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to links. 
	 * @param value the item to add to links
	 */
	public void addToLinks(final SessionContext ctx, final CMSLinkComponent value)
	{
		addLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to links. 
	 * @param value the item to add to links
	 */
	public void addToLinks(final CMSLinkComponent value)
	{
		addToLinks( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from links. 
	 * @param value the item to remove from links
	 */
	public void removeFromLinks(final SessionContext ctx, final CMSLinkComponent value)
	{
		removeLinkedItems( 
			ctx,
			true,
			MultitenantConstants.Relations.LINKSFORLISTCOMPONENT1,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from links. 
	 * @param value the item to remove from links
	 */
	public void removeFromLinks(final CMSLinkComponent value)
	{
		removeFromLinks( getSession().getSessionContext(), value );
	}
	
}
