package se.sundsvall.businessengagements.integration.bolagsverket.ssbten;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import se.bolagsverket.schema.ssbten.engagemang.EngagemangBegaran;
import se.bolagsverket.schema.ssbten.engagemang.EngagemangSvar;
import se.sundsvall.dept44.problem.Problem;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
@CircuitBreaker(name = "SsbtenIntegration")
public class SsbtenIntegration {

	private final WebServiceTemplate webServiceTemplate;

	public SsbtenIntegration(
		@Qualifier("bolagsverketSsbtenWebserviceTemplate") final WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

	public EngagemangSvar callBolagsverket(EngagemangBegaran engagemangBegaranRequest) {
		try {
			return (EngagemangSvar) webServiceTemplate.marshalSendAndReceive(engagemangBegaranRequest);
		} catch (final Exception e) {
			throw Problem.builder()
				.withTitle("Error while getting digital engagements from bolagsverket/ssbten")
				.withStatus(INTERNAL_SERVER_ERROR)
				.withDetail(e.getMessage())
				.build();
		}
	}
}
