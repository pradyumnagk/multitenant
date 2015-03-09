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

/**
 * Generated class for type {@link com.clients.jalo.cms2.components.CategoryTreeComponent CategoryTreeComponent1}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCategoryTreeComponent extends SimpleCMSComponent
{
	/** Qualifier of the <code>CategoryTreeComponent1.useFacets</code> attribute **/
	public static final String USEFACETS = "useFacets".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryTreeComponent1.useFacets</code> attribute.
	 * @return the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public Boolean isUseFacets(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, USEFACETS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryTreeComponent1.useFacets</code> attribute.
	 * @return the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public Boolean isUseFacets()
	{
		return isUseFacets( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryTreeComponent1.useFacets</code> attribute. 
	 * @return the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public boolean isUseFacetsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isUseFacets( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CategoryTreeComponent1.useFacets</code> attribute. 
	 * @return the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public boolean isUseFacetsAsPrimitive()
	{
		return isUseFacetsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryTreeComponent1.useFacets</code> attribute. 
	 * @param value the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public void setUseFacets(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, USEFACETS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryTreeComponent1.useFacets</code> attribute. 
	 * @param value the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public void setUseFacets(final Boolean value)
	{
		setUseFacets( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryTreeComponent1.useFacets</code> attribute. 
	 * @param value the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public void setUseFacets(final SessionContext ctx, final boolean value)
	{
		setUseFacets( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CategoryTreeComponent1.useFacets</code> attribute. 
	 * @param value the useFacets - If set to true and a FacetSearch is assigned to the current catalogversion,
	 * 						the element will use facets as navigation. If no FacetSearchItem is available for
	 * 						the current catalogversion, it will fallback to the category tree navigation.
	 */
	public void setUseFacets(final boolean value)
	{
		setUseFacets( getSession().getSessionContext(), value );
	}
	
}
