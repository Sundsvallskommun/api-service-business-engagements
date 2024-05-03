package openapi;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static se.sundsvall.dept44.util.ResourceUtils.asString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import se.sundsvall.businessengagements.BusinessEngagements;

@SpringBootTest(
	webEnvironment = RANDOM_PORT,
	classes = BusinessEngagements.class,
	properties = {
		"spring.main.banner-mode=off",
		"logging.level.se.sundsvall.dept44.payload=OFF"
	}
)
@ActiveProfiles("it")
class OpenApiSpecificationIT {

	private final YAMLMapper yamlMapper = new YAMLMapper();

	@Value("classpath:/openapi.yml")
	private Resource openApiResource;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void compareOpenApiSpecifications() {
		final var existingOpenApiSpecification = asString(openApiResource);
		final var currentOpenApiSpecification = getCurrentOpenApiSpecification();

		assertThatJson(toJson(existingOpenApiSpecification))
			.withOptions(IGNORING_ARRAY_ORDER)
			.whenIgnoringPaths("servers")
			.isEqualTo(toJson(currentOpenApiSpecification));
	}

	/**
	 * Fetches and returns the current OpenAPI specification in YAML format.
	 *
	 * @return the current OpenAPI specification
	 */
	private String getCurrentOpenApiSpecification() {
		return webTestClient.get().uri("/api-docs")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class)
			.returnResult()
			.getResponseBody();
	}

	/**
	 * Attempts to convert the given YAML (no YAML-check...) to JSON.
	 *
	 * @param yaml the YAML to convert
	 * @return a JSON string
	 */
	private String toJson(final String yaml) {
		try {
			return yamlMapper.readTree(yaml).toString();
		} catch (final JsonProcessingException e) {
			throw new IllegalStateException("Unable to convert YAML to JSON", e);
		}
	}

}