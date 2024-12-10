package se.sundsvall.businessengagements.integration.bolagsverket.ssbtgu;

import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.zalando.problem.Problem;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaran;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterSvar;

@Component
@CircuitBreaker(name = "SsbtguIntegration")
public class SsbtguIntegration {

	private final WebServiceTemplate webServiceTemplate;

	public SsbtguIntegration(
		@Qualifier("bolagsverketSsbtguWebserviceTemplate") final WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

	public GrundlaggandeUppgifterSvar callBolagsverket(GrundlaggandeUppgifterBegaran request) {
		try {
			return (GrundlaggandeUppgifterSvar) webServiceTemplate.marshalSendAndReceive(request);
		} catch (Exception e) {
			throw Problem.builder()
				.withTitle("Error while getting business information from bolagsverket/ssbtgu")
				.withStatus(INTERNAL_SERVER_ERROR)
				.withDetail(e.getMessage())
				.build();
		}
	}

}
