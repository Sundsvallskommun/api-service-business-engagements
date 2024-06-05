package se.sundsvall.businessengagements.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static se.sundsvall.businessengagements.integration.party.PartyIntegration.LEGAL_ID_CACHE;
import static se.sundsvall.businessengagements.integration.party.PartyIntegration.PARTY_ID_CACHE;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.BUSINESS_ENGAGEMENTS_CACHE;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.BUSINESS_INFORMATION_CACHE;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.NoOpCacheManager;

@ExtendWith(MockitoExtension.class)
class CacheConfigTest {

	@Mock
	private CacheProperties mockCacheProperties;

	@InjectMocks
	private CacheConfig cacheConfig;

	@BeforeEach
	void setup() {
		when(mockCacheProperties.expireAfterWrite()).thenReturn(Duration.ofSeconds(5));
		when(mockCacheProperties.maximumSize()).thenReturn(100);
	}

	@Test
	void testCacheManager_whencacheIsEnabled() {
		when(mockCacheProperties.enabled()).thenReturn(true);
		var cacheManager = cacheConfig.cacheManager(cacheConfig.caffeineConfig());

		assertThat(cacheManager).isInstanceOf(CaffeineCacheManager.class);
		assertThat(cacheManager.getCacheNames()).containsExactlyInAnyOrder(LEGAL_ID_CACHE, PARTY_ID_CACHE, BUSINESS_INFORMATION_CACHE, BUSINESS_ENGAGEMENTS_CACHE);
	}

	@Test
	void testCacheManager_whenCacheIsDisabled() {
		when(mockCacheProperties.enabled()).thenReturn(false);
		var cacheManager = cacheConfig.cacheManager(cacheConfig.caffeineConfig());

		assertThat(cacheManager).isInstanceOf(NoOpCacheManager.class);
		assertThat(cacheManager.getCacheNames()).isEmpty();
	}
}
