package se.sundsvall.businessengagements.integration.db;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Scheduler for removing expired cache entries from the database.
 * Checks for expired entities every half hour.
 */
@Component
@EnableScheduling
public class DbScheduledTasks {

	private static final Logger LOG = LoggerFactory.getLogger(DbScheduledTasks.class);

	/**
	 * How long entities should live in the cache/db
	 *
	 * @return
	 */
	private final Duration entityCacheTtl;

	private final EngagementsCacheRepository repository;

	public DbScheduledTasks(final EngagementsCacheRepository repository,
		@Value("${bolagsverket.entity.ttl}")
		Duration entityCacheTtl) {
		this.repository = repository;
		this.entityCacheTtl = entityCacheTtl;
	}

	/**
	 * <pre>
	 * Checks for expired entries every other minutes.
	 * Since it's the timestamp in the DB that determines if an entity should be removed or not
	 * it's not that important with the interval.
	 * </pre>
	 */
	@Scheduled(initialDelay = 1, fixedRate = 2, timeUnit = TimeUnit.MINUTES)
	//delay it since we don't want it to run immediately on startup
	@Transactional
	public void removeOldCacheEntries() {
		LOG.debug("Checking for expired entities");
		LocalDateTime deleteBefore = LocalDateTime.now().minus(entityCacheTtl);
		int amountRemoved = repository.deleteAllByUpdatedBefore(deleteBefore);

		//Don't clutter logs if we don't have anything to remove.
		if (amountRemoved > 0) {
			LOG.info("Scheduled task found {} old entities and removed them", amountRemoved);
		}
	}

}
