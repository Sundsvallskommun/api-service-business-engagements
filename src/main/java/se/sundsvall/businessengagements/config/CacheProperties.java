package se.sundsvall.businessengagements.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "appconfig.cache")
public record CacheProperties(
	boolean enabled,
	Duration expireAfterWrite,
	int maximumSize
) {
}
