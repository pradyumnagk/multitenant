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
package com.clients.jalo;

import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.core.Registry;
import de.hybris.platform.impex.jalo.ImpExManager;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.solrfacetsearch.constants.SolrfacetsearchConstants;
import de.hybris.platform.solrfacetsearch.jalo.SolrfacetsearchManager;
import de.hybris.platform.solrfacetsearch.jalo.config.SolrFacetSearchConfig;
import de.hybris.platform.solrfacetsearch.jalo.indexer.cron.SolrIndexerCronJob;
import com.clients.constants.MultitenantConstants;
import de.hybris.platform.util.CSVConstants;
import de.hybris.platform.util.JspContext;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;





/**
 * This is the extension manager of the Multitenant extension.
 */
public class MultitenantManager extends GeneratedMultitenantManager
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(MultitenantManager.class.getName());

	/**
	 * Get the valid instance of this manager.
	 * 
	 * @return the current instance of this manager
	 */
	public static MultitenantManager getInstance()
	{
		return (MultitenantManager) Registry.getCurrentTenant().getJaloConnection().getExtensionManager().getExtension(
				MultitenantConstants.EXTENSIONNAME);
	}

	/**
	 * Implement this method to create initial objects. This method will be called by system creator during
	 * initialization and system update. Be sure that this method can be called repeatedly.
	 * 
	 * An example usage of this method is to create required cronjobs or modifying the type system (setting e.g some
	 * default values)
	 * 
	 * @param params
	 *           the parameters provided by user for creation of objects for the extension
	 * @param jspc
	 *           the jsp context; you can use it to write progress information to the jsp page during creation
	 */
	@Override
	public void createEssentialData(final Map<String, String> params, final JspContext jspc)
	{
		// implement here code creating essential data
	}

	/**
	 * Implement this method to create data that is used in your project. This method will be called during the system
	 * initialization.
	 * 
	 * An example use is to import initial data like currencies or languages for your project from an csv file.
	 * 
	 * @param params
	 *           the parameters provided by user for creation of objects for the extension
	 * @param jspc
	 *           the jsp context; you can use it to write progress information to the jsp page during creation
	 */
	@Override
	public void createProjectData(final Map<String, String> params, final JspContext jspc) throws Exception
	{
		ImpExManager.getInstance().importData(
				MultitenantManager.class.getResourceAsStream("/multitenant/import/cockpit/ui_components.csv"), "utf-8",
				CSVConstants.HYBRIS_FIELD_SEPARATOR, CSVConstants.HYBRIS_QUOTE_CHARACTER, true);

		final List<String> tenantlist = getTenant().getTenantSpecificExtensionNames();
		if (tenantlist.contains("solrfacetsearch") && (tenantlist.contains("sampledata") || tenantlist.contains("testdata")))
		{
			try
			{
				final SolrfacetsearchManager solrfacetsearchManager = SolrfacetsearchManager.getInstance();
				final EnumerationValue fullIndex = EnumerationManager.getInstance().getEnumerationValue(
						SolrfacetsearchConstants.TC.INDEXEROPERATIONVALUES,
						SolrfacetsearchConstants.Enumerations.IndexerOperationValues.FULL);

				final CatalogVersion hwCatalog = getCatalogVersion("hwcatalog", "Online");
				if (hwCatalog != null)
				{
					final List<SolrFacetSearchConfig> hwCatalogConfigs = solrfacetsearchManager.getFacetSearchConfigs(hwCatalog);
					if (hwCatalogConfigs != null)
					{
						for (final SolrFacetSearchConfig solrFacetSearchConfig : hwCatalogConfigs)
						{
							final SolrIndexerCronJob cronJob = solrfacetsearchManager.createSolrIndexerCronJob(solrFacetSearchConfig,
									fullIndex);
							cronJob.getJob().perform(cronJob, true);
						}
					}
				}

				final CatalogVersion clothesCatalog = getCatalogVersion("clothescatalog", "Online");
				if (clothesCatalog != null)
				{
					final List<SolrFacetSearchConfig> clothesCatalogConfigs = solrfacetsearchManager
							.getFacetSearchConfigs(clothesCatalog);
					if (clothesCatalogConfigs != null)
					{
						for (final SolrFacetSearchConfig solrFacetSearchConfig : clothesCatalogConfigs)
						{
							final SolrIndexerCronJob cronJob = solrfacetsearchManager.createSolrIndexerCronJob(solrFacetSearchConfig,
									fullIndex);
							cronJob.getJob().perform(cronJob, true);
						}
					}
				}
			}
			catch (final Exception e)
			{
				LOG.error(e.getMessage(), e);
			}



			//set attribute 'useFacets' to true for the right category tree
			ImpExManager.getInstance().importData(
					MultitenantManager.class.getResourceAsStream("/multitenant/import/cockpit/category_tree_component.csv"),
					"utf-8", CSVConstants.HYBRIS_FIELD_SEPARATOR, CSVConstants.HYBRIS_QUOTE_CHARACTER, true);

			//import preview multitenant deeplink rules
			ImpExManager.getInstance().importData(
					MultitenantManager.class
							.getResourceAsStream("/multitenant/import/cockpit/multitenant_preview_depplink_rules.csv"), "utf-8",
					CSVConstants.HYBRIS_FIELD_SEPARATOR, CSVConstants.HYBRIS_QUOTE_CHARACTER, true);

		}
	}

	private CatalogVersion getCatalogVersion(final String catalogId, final String version) throws JaloItemNotFoundException
	{
		CatalogVersion ret = null;

		final CatalogManager cm = CatalogManager.getInstance();

		// get catalog			
		final Catalog srcCat = cm.getCatalog(catalogId);
		if (srcCat == null)
		{
			throw new JaloItemNotFoundException("Catalog '" + catalogId + "' not available.", 0);
		}
		else
		{
			ret = srcCat.getCatalogVersion(version);
			if (ret == null)
			{
				throw new JaloItemNotFoundException(
						"Catalog version '" + version + "' of catalog '" + catalogId + "' not available.", 0);
			}
		}
		return ret;
	}
	//
	//	@SuppressWarnings("deprecation")
	//	private void synchronizeCatVersions(final List<CatVersionSyncData> synchData)
	//	{
	//		if (synchData != null && !synchData.isEmpty())
	//		{
	//			if (LOG.isInfoEnabled())
	//			{
	//				LOG.info("Setting up synchronization jobs...");
	//			}
	//			final List<SyncItemJob> jobs = setupSynchronizationJobs(synchData);
	//
	//			if (!jobs.isEmpty())
	//			{
	//				if (LOG.isInfoEnabled())
	//				{
	//					final int size = jobs.size();
	//					LOG.info("Running " + size + " synchronization job" + (size == 1 ? "" : "s") + "...");
	//				}
	//				for (final SyncItemJob job : jobs)
	//				{
	//					try
	//					{
	//						performSynchronization(job);
	//						LOG.info("\t" + job.getCode() + " - OK");
	//					}
	//					catch (final Exception e)
	//					{
	//						LOG.warn("\t" + job.getCode() + " - FAILED (Reason: " + e.getMessage() + ")");
	//						continue;
	//					}
	//				}
	//			}
	//		}
	//	}
	//
	//	@SuppressWarnings("deprecation")
	//	private List<SyncItemJob> setupSynchronizationJobs(final List<CatVersionSyncData> synchs)
	//	{
	//		final List<SyncItemJob> jobs = new ArrayList<SyncItemJob>();
	//
	//		if (synchs == null || synchs.isEmpty())
	//		{
	//			return Collections.EMPTY_LIST;
	//		}
	//
	//		try
	//		{
	//			ImpExManager.getInstance().importData(
	//					MultitenantManager.class.getResourceAsStream("/sampledata/multitenant_jobs.csv"), "utf-8",
	//					CSVConstants.HYBRIS_FIELD_SEPARATOR, CSVConstants.HYBRIS_QUOTE_CHARACTER, true);
	//		}
	//		catch (final Exception e)
	//		{
	//			LOG.warn("Could not setup catalog version synchronization job. Reason: Required import script failed.", e);
	//			return Collections.EMPTY_LIST;
	//		}
	//
	//		// import catalog version synch job
	//
	//		for (final CatVersionSyncData synchData : synchs)
	//		{
	//			CatalogVersion src = null;
	//			CatalogVersion tgt = null;
	//
	//			try
	//			{
	//				src = getCatalogVersion(synchData.getSrcCatalogId(), synchData.getSrcCatalogVersion());
	//				tgt = getCatalogVersion(synchData.getTgtCatalogId(), synchData.getTgtCatalogVersion());
	//			}
	//			catch (final JaloItemNotFoundException jinfe)
	//			{
	//				LOG.warn("Could not setup catalog version synchronization job. Reason: " + jinfe.getMessage());
	//				continue;
	//			}
	//
	//			// configure root types		
	//			final SyncItemJob syncJob = CatalogManager.getInstance().getSyncJob(src, tgt, "StoreTemplate catalog: staged -> online");
	//			if (syncJob == null)
	//			{
	//				LOG.warn("Could not setup catalog version synchronization job. Reason: Synchronization job not found.");
	//				continue;
	//			}
	//			else
	//			{
	//				final List<ComposedType> rootTypes = new ArrayList<ComposedType>(2);
	//				rootTypes.add(TypeManager.getInstance().getComposedType(Cms2Constants.TC.CMSITEM));
	//				rootTypes.add(TypeManager.getInstance().getComposedType(Cms2Constants.TC.CMSRELATION));
	//				syncJob.setRootTypes(JaloSession.getCurrentSession().getSessionContext(), rootTypes);
	//
	//				jobs.add(syncJob);
	//			}
	//		}
	//
	//		return jobs;
	//	}
	//
	//	@SuppressWarnings("deprecation")
	//	private void performSynchronization(final SyncItemJob job) throws Exception
	//	{
	//		final SyncItemCronJob cj = job.newExecution();
	//		cj.setLogToDatabase(false);
	//		cj.setLogToFile(false);
	//		cj.setForceUpdate(false);
	//		if (LOG.isDebugEnabled())
	//		{
	//			LOG.debug("Generating cronjob " + cj.getCode() + " to synchronize staged to online version, configuring ...");
	//		}
	//		job.configureFullVersionSync(cj);
	//		if (LOG.isDebugEnabled())
	//		{
	//			LOG.debug("Starting synchronization, this may take a while ...");
	//		}
	//		job.perform(cj, true);
	//	}
	//
	//	private class CatVersionSyncData
	//	{
	//		private final String srcCatalogId;
	//		private final String srcCatalogVersion;
	//		private final String tgtCatalogId;
	//		private final String tgtCatalogVersion;
	//
	//		public CatVersionSyncData(final String srcCatalogId, final String srcCatalogVersion, final String tgtCatalogId,
	//				final String tgtCatalogVersion)
	//		{
	//			this.srcCatalogId = srcCatalogId;
	//			this.srcCatalogVersion = srcCatalogVersion;
	//			this.tgtCatalogId = tgtCatalogId;
	//			this.tgtCatalogVersion = tgtCatalogVersion;
	//		}
	//
	//		public String getSrcCatalogId()
	//		{
	//			return srcCatalogId;
	//		}
	//
	//		public String getSrcCatalogVersion()
	//		{
	//			return srcCatalogVersion;
	//		}
	//
	//		public String getTgtCatalogId()
	//		{
	//			return tgtCatalogId;
	//		}
	//
	//		public String getTgtCatalogVersion()
	//		{
	//			return tgtCatalogVersion;
	//		}
	//	}
}
