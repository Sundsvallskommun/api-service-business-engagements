package se.sundsvall.businessengagements.config;

import static se.sundsvall.businessengagements.integration.party.PartyIntegration.LEGAL_ID_CACHE;
import static se.sundsvall.businessengagements.integration.party.PartyIntegration.PARTY_ID_CACHE;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.BUSINESS_ENGAGEMENTS_CACHE;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.BUSINESS_INFORMATION_CACHE;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

	private final CacheProperties cacheProperties;

	public CacheConfig(CacheProperties cacheProperties) {
		this.cacheProperties = cacheProperties;
	}

	@Bean
	public Caffeine<Object, Object> caffeineConfig() {
		return Caffeine.newBuilder()
			.expireAfterWrite(cacheProperties.expireAfterWrite())
			.maximumSize(cacheProperties.maximumSize())
			.recordStats();
	}

	/**
	 * Will configure a CacheManager if caching is enabled, otherwise a NoOpCacheManager (disabled cache) will be returned.
	 * @param caffeine Caffeine configuration
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
		if(cacheProperties.enabled()) {
			var caffeineCacheManager = new CaffeineCacheManager(LEGAL_ID_CACHE, PARTY_ID_CACHE, BUSINESS_INFORMATION_CACHE, BUSINESS_ENGAGEMENTS_CACHE);
			caffeineCacheManager.setCaffeine(caffeine);
			return caffeineCacheManager;
		}

		return new NoOpCacheManager();
	}
}
