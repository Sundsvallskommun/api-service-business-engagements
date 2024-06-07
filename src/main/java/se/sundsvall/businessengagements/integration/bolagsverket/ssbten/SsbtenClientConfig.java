package se.sundsvall.businessengagements.integration.bolagsverket.ssbten;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.zalando.logbook.Logbook;

import se.sundsvall.businessengagements.integration.bolagsverket.SsbtProperties;
import se.sundsvall.dept44.configuration.webservicetemplate.WebServiceTemplateBuilder;

@Configuration
public class SsbtenClientConfig {

	private static final Logger LOG = LoggerFactory.getLogger(SsbtenClientConfig.class);

	private final SsbtProperties properties;

	private final Logbook logbook;

	public SsbtenClientConfig(final SsbtProperties properties, final Logbook logbook) {
		this.properties = properties;
		this.logbook = logbook;
	}

	@Bean(name = "bolagsverket-ssbten-webservice-template")
	public WebServiceTemplate getSsbtenWebserviceTemplate() {

		var builder = new WebServiceTemplateBuilder()
			.withBaseUrl(properties.ssbtenApiUrl())
			.withPackageToScan("se.bolagsverket.schema.ssbten")
			.withReadTimeout(properties.readTimeout())
			.withConnectTimeout(properties.connectTimeout())
			.withLogbook(logbook);

		if (properties.shouldUseKeyStore()) {
			loadKeyStore(builder);
		} else {
			LOG.info("Not using any keystore for Bolagsverket");
		}

		return builder.build();
	}

	private void loadKeyStore(final WebServiceTemplateBuilder builder) {
		builder.withKeyStoreData(Base64.getDecoder().decode(properties.keyStoreAsBase64().getBytes(StandardCharsets.UTF_8)))
			.withKeyStorePassword(properties.keystorePassword());
	}
}
