package se.sundsvall.businessengagements.integration.bolagsverket.ssbtgu;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.zalando.logbook.Logbook;

import se.sundsvall.businessengagements.integration.bolagsverket.SsbtProperties;
import se.sundsvall.dept44.configuration.webservicetemplate.WebServiceTemplateBuilder;

@Configuration
public class SsbtguClientConfig {

	private static final Logger LOG = LoggerFactory.getLogger(SsbtguClientConfig.class);

	private final SsbtProperties properties;

	private final Logbook logbook;

	@Autowired
	public SsbtguClientConfig(final SsbtProperties properties, final Logbook logbook) {
		this.properties = properties;
		this.logbook = logbook;
	}

	@Bean(name = "bolagsverket-ssbtgu-webservice-template")
	public WebServiceTemplate getSsbtguWebserviceTemplate() {

		final WebServiceTemplateBuilder builder = new WebServiceTemplateBuilder()
			.withBaseUrl(properties.ssbtguApiUrl())
			.withPackageToScan("se.bolagsverket.schema.ssbtgu")
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
		builder
			.withKeyStoreData(Base64.getDecoder()
				.decode(properties.keyStoreAsBase64().getBytes(StandardCharsets.UTF_8)))
			.withKeyStorePassword(properties.keystorePassword());
	}

}
