/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 16 Jan, 2015 12:50:02 AM                    ---
 * ----------------------------------------------------------------
 */
package com.clients.jalo.cms2.components;

import com.clients.constants.MultitenantConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;

/**
 * Generated class for type {@link com.clients.jalo.cms2.components.CartAndWishlistComponent CartAndWishlistComponent1}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCartAndWishlistComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>CartAndWishlistComponent1.mode</code> attribute **/
	public static final String MODE = "mode".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartAndWishlistComponent1.mode</code> attribute.
	 * @return the mode
	 */
	public EnumerationValue getMode(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, MODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartAndWishlistComponent1.mode</code> attribute.
	 * @return the mode
	 */
	public EnumerationValue getMode()
	{
		return getMode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartAndWishlistComponent1.mode</code> attribute. 
	 * @param value the mode
	 */
	public void setMode(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, MODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartAndWishlistComponent1.mode</code> attribute. 
	 * @param value the mode
	 */
	public void setMode(final EnumerationValue value)
	{
		setMode( getSession().getSessionContext(), value );
	}
	
}
