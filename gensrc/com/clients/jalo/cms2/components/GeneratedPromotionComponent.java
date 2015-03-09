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
import de.hybris.platform.promotions.jalo.ProductPromotion;

/**
 * Generated class for type {@link com.clients.jalo.cms2.components.PromotionComponent PromotionComponent1}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedPromotionComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>PromotionComponent1.promotion</code> attribute **/
	public static final String PROMOTION = "promotion".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionComponent1.promotion</code> attribute.
	 * @return the promotion
	 */
	public ProductPromotion getPromotion(final SessionContext ctx)
	{
		return (ProductPromotion)getProperty( ctx, PROMOTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PromotionComponent1.promotion</code> attribute.
	 * @return the promotion
	 */
	public ProductPromotion getPromotion()
	{
		return getPromotion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionComponent1.promotion</code> attribute. 
	 * @param value the promotion
	 */
	public void setPromotion(final SessionContext ctx, final ProductPromotion value)
	{
		setProperty(ctx, PROMOTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PromotionComponent1.promotion</code> attribute. 
	 * @param value the promotion
	 */
	public void setPromotion(final ProductPromotion value)
	{
		setPromotion( getSession().getSessionContext(), value );
	}
	
}
