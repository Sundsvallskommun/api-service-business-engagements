package se.sundsvall.businessengagements.integration.bolagsverket;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "integration.bolagsverket")
public record SsbtProperties(
	String ssbtenApiUrl,

	String ssbtguApiUrl,

	Duration readTimeout,

	Duration connectTimeout,

	String keyStoreAsBase64,

	String keystorePassword,
	boolean shouldUseKeyStore) {

}
