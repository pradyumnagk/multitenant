/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 16 Jan, 2015 12:50:02 AM                    ---
 * ----------------------------------------------------------------
 */
package com.clients.jalo.cms2.components;

import com.clients.constants.MultitenantConstants;
import de.hybris.platform.cms2.jalo.contents.components.SimpleCMSComponent;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.media.Media;
import java.util.Map;

/**
 * Generated class for type {@link com.clients.jalo.cms2.components.CartSummaryComponent CartSummaryComponent1}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCartSummaryComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>CartSummaryComponent1.suplementaryText</code> attribute **/
	public static final String SUPLEMENTARYTEXT = "suplementaryText".intern();
	/** Qualifier of the <code>CartSummaryComponent1.cartImage</code> attribute **/
	public static final String CARTIMAGE = "cartImage".intern();
	/** Qualifier of the <code>CartSummaryComponent1.cartTitle</code> attribute **/
	public static final String CARTTITLE = "cartTitle".intern();
	/** Qualifier of the <code>CartSummaryComponent1.enabled</code> attribute **/
	public static final String ENABLED = "enabled".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.cartImage</code> attribute.
	 * @return the cartImage
	 */
	public Media getCartImage(final SessionContext ctx)
	{
		return (Media)getProperty( ctx, CARTIMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.cartImage</code> attribute.
	 * @return the cartImage
	 */
	public Media getCartImage()
	{
		return getCartImage( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.cartImage</code> attribute. 
	 * @param value the cartImage
	 */
	public void setCartImage(final SessionContext ctx, final Media value)
	{
		setProperty(ctx, CARTIMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.cartImage</code> attribute. 
	 * @param value the cartImage
	 */
	public void setCartImage(final Media value)
	{
		setCartImage( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.cartTitle</code> attribute.
	 * @return the cartTitle
	 */
	public String getCartTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCartSummaryComponent.getCartTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, CARTTITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.cartTitle</code> attribute.
	 * @return the cartTitle
	 */
	public String getCartTitle()
	{
		return getCartTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.cartTitle</code> attribute. 
	 * @return the localized cartTitle
	 */
	public Map<Language,String> getAllCartTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,CARTTITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.cartTitle</code> attribute. 
	 * @return the localized cartTitle
	 */
	public Map<Language,String> getAllCartTitle()
	{
		return getAllCartTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.cartTitle</code> attribute. 
	 * @param value the cartTitle
	 */
	public void setCartTitle(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCartSummaryComponent.setCartTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, CARTTITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.cartTitle</code> attribute. 
	 * @param value the cartTitle
	 */
	public void setCartTitle(final String value)
	{
		setCartTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.cartTitle</code> attribute. 
	 * @param value the cartTitle
	 */
	public void setAllCartTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,CARTTITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.cartTitle</code> attribute. 
	 * @param value the cartTitle
	 */
	public void setAllCartTitle(final Map<Language,String> value)
	{
		setAllCartTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.enabled</code> attribute.
	 * @return the enabled
	 */
	public abstract Boolean isEnabled(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.enabled</code> attribute.
	 * @return the enabled
	 */
	public Boolean isEnabled()
	{
		return isEnabled( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.enabled</code> attribute. 
	 * @return the enabled
	 */
	public boolean isEnabledAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isEnabled( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.enabled</code> attribute. 
	 * @return the enabled
	 */
	public boolean isEnabledAsPrimitive()
	{
		return isEnabledAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.suplementaryText</code> attribute.
	 * @return the suplementaryText
	 */
	public String getSuplementaryText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCartSummaryComponent.getSuplementaryText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SUPLEMENTARYTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.suplementaryText</code> attribute.
	 * @return the suplementaryText
	 */
	public String getSuplementaryText()
	{
		return getSuplementaryText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.suplementaryText</code> attribute. 
	 * @return the localized suplementaryText
	 */
	public Map<Language,String> getAllSuplementaryText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SUPLEMENTARYTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CartSummaryComponent1.suplementaryText</code> attribute. 
	 * @return the localized suplementaryText
	 */
	public Map<Language,String> getAllSuplementaryText()
	{
		return getAllSuplementaryText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.suplementaryText</code> attribute. 
	 * @param value the suplementaryText
	 */
	public void setSuplementaryText(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCartSummaryComponent.setSuplementaryText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SUPLEMENTARYTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.suplementaryText</code> attribute. 
	 * @param value the suplementaryText
	 */
	public void setSuplementaryText(final String value)
	{
		setSuplementaryText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.suplementaryText</code> attribute. 
	 * @param value the suplementaryText
	 */
	public void setAllSuplementaryText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SUPLEMENTARYTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CartSummaryComponent1.suplementaryText</code> attribute. 
	 * @param value the suplementaryText
	 */
	public void setAllSuplementaryText(final Map<Language,String> value)
	{
		setAllSuplementaryText( getSession().getSessionContext(), value );
	}
	
}
