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
package com.clients.web.servlets;

import de.hybris.platform.btg.events.AbstractBTGRuleDataEvent;
import de.hybris.platform.servicelayer.event.EventService;
import com.clients.web.servlets.util.FilterSpringUtil;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * Request listener that provides infrastructure required to perform BTG Rule Data Event handling.
 */
public class RequestScopedBTGRuleDataServletRequestListener implements ServletRequestListener
{
	private final static Logger LOG = Logger.getLogger(RequestScopedBTGRuleDataServletRequestListener.class.getName());
	private static final String DATA_REMOVING_EVENTS_COLLECTION_ATTRNAME = "DATA_REMOVING_EVENTS_COLLECTION_ATTRNAME";

	@Override
	public void requestInitialized(final ServletRequestEvent servletRequestEvent)
	{
		//Create collection for holding data removing events
		servletRequestEvent.getServletRequest().setAttribute(DATA_REMOVING_EVENTS_COLLECTION_ATTRNAME,
				new ArrayList<AbstractBTGRuleDataEvent<Serializable>>());
	}


	@Override
	public void requestDestroyed(final ServletRequestEvent servletRequestEvent)
	{
		servletRequestEvent.getServletRequest().removeAttribute(DATA_REMOVING_EVENTS_COLLECTION_ATTRNAME);

	}

	/**
	 * Publishes a BTG Rule Data Event. It can be data inserting event or data removing event.
	 */
	public static <T extends Serializable> void publishEvent(final AbstractBTGRuleDataEvent<T> event,
			final HttpServletRequest httpServletRequest)
	{
		final EventService eventService = getEventService(httpServletRequest);
		eventService.publishEvent(event);
	}

	/**
	 * Publishes a data inserting BTG Rule Data Event. It also automatically creates a corresponding data removing BTG
	 * Rule Data Event, which will be published automatically at the end of request processing. This ensures that the
	 * scope of the data is restricted to single-request only.
	 */
	public static <T extends Serializable> void publishRequestScopedDataInsertingEvent(
			final AbstractBTGRuleDataEvent<T> dataInsertingEvent, final HttpServletRequest httpServletRequest)
	{
		try
		{
			final Constructor<? extends AbstractBTGRuleDataEvent> constructor = dataInsertingEvent.getClass()
					.getDeclaredConstructor(dataInsertingEvent.getClass());
			final AbstractBTGRuleDataEvent dataRemovingEvent = constructor.newInstance(dataInsertingEvent);

			publishEvent(dataInsertingEvent, httpServletRequest);

			getDataRemovingEventsCollection(httpServletRequest).add(dataRemovingEvent);
		}
		catch (final NoSuchMethodException ex)
		{
			LOG.error("Could not publish event " + dataInsertingEvent.getClass(), ex);
		}
		catch (final InvocationTargetException ex)
		{
			LOG.error("Could not publish event " + dataInsertingEvent.getClass(), ex);
		}
		catch (final IllegalArgumentException ex)
		{
			LOG.error("Could not publish event " + dataInsertingEvent.getClass(), ex);
		}
		catch (final InstantiationException ex)
		{
			LOG.error("Could not publish event " + dataInsertingEvent.getClass(), ex);
		}
		catch (final IllegalAccessException ex)
		{
			LOG.error("Could not publish event " + dataInsertingEvent.getClass(), ex);
		}
	}

	/**
	 * Publishes in a single step all data removing events collected for data inserting events published using
	 * {@link #publishRequestScopedDataInsertingEvent(AbstractBTGRuleDataEvent, HttpServletRequest)}.
	 */
	public static void publishDataRemovingEvents(final HttpServletRequest httpServletRequest)
	{
		//Iterate over collection of data removing events and launch them.
		//Launch removing events in the reverse order of the inserting events.
		final List<AbstractBTGRuleDataEvent<Serializable>> dataRemovingEvents = getDataRemovingEventsCollection(httpServletRequest);

		if (dataRemovingEvents != null)
		{
			Collections.reverse(dataRemovingEvents);
			final Iterator<AbstractBTGRuleDataEvent<Serializable>> eventIterator = dataRemovingEvents.iterator();
			final EventService eventService = getEventService(httpServletRequest);

			while (eventIterator.hasNext())
			{
				final AbstractBTGRuleDataEvent<Serializable> event = eventIterator.next();
				eventService.publishEvent(event);
			}
		}
	}

	private static List<AbstractBTGRuleDataEvent<Serializable>> getDataRemovingEventsCollection(final ServletRequest servletRequest)
	{
		final List<AbstractBTGRuleDataEvent<Serializable>> result = (List<AbstractBTGRuleDataEvent<Serializable>>) servletRequest
				.getAttribute(DATA_REMOVING_EVENTS_COLLECTION_ATTRNAME);
		return result;
	}

	private static EventService getEventService(final HttpServletRequest httpServletRequest)
	{
		return FilterSpringUtil.getSpringBean(httpServletRequest, "eventService", EventService.class);
	}
}
