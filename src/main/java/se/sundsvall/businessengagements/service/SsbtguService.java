package se.sundsvall.businessengagements.service;

import static org.zalando.problem.Status.BAD_GATEWAY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;

import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.integration.bolagsverket.ssbtgu.SsbtguIntegration;
import se.sundsvall.businessengagements.service.mapper.ssbtgu.SsbtguRequestMapper;
import se.sundsvall.businessengagements.service.mapper.ssbtgu.SsbtguResponseMapper;

@Component
public class SsbtguService {

	private static final Logger LOG = LoggerFactory.getLogger(SsbtguService.class);

	private final SsbtguRequestMapper ssbtguRequestMapper;

	private final SsbtguResponseMapper ssbtguResponseMapper;

	private final SsbtguIntegration ssbtguIntegration;

	private final boolean shouldValidateResponse;

	public SsbtguService(SsbtguRequestMapper ssbtguRequestMapper, SsbtguResponseMapper ssbtguResponseMapper,
		SsbtguIntegration ssbtguIntegration, @Value("${integration.bolagsverket.should-verify-transactionid}") boolean shouldValidateResponse) {
		this.ssbtguRequestMapper = ssbtguRequestMapper;
		this.ssbtguResponseMapper = ssbtguResponseMapper;
		this.ssbtguIntegration = ssbtguIntegration;
		this.shouldValidateResponse = shouldValidateResponse;
	}

	public BusinessInformation fetchBusinessInformation(String orgNumber, String orgName) {
		var grundlaggandeUppgifterBegaran = ssbtguRequestMapper.createGrundlaggandeUppgifterBegaran(orgNumber, orgName);
		var grundlaggandeUppgifterSvar = ssbtguIntegration.callBolagsverket(grundlaggandeUppgifterBegaran);

		var outTransaktionId = grundlaggandeUppgifterBegaran.getGrundlaggandeUppgifterBegaranMetadata().getTransaktionId();
		var inTransationId = grundlaggandeUppgifterSvar.getGrundlaggandeUppgifterSvarMetadata().getTransaktionId();

		validateResponse(outTransaktionId, inTransationId);

		return ssbtguResponseMapper.mapGrundlaggandeUppgifterSvar(grundlaggandeUppgifterSvar);
	}

	void validateResponse(String outTransaktionId, String inTransationId) {
		if (shouldValidateResponse && !outTransaktionId.equals(inTransationId)) {
			LOG.warn("Incoming transactionId: {} doesn't match outgoing transactionId: {}", inTransationId, outTransaktionId);
			throw Problem.builder()
				.withTitle("Response from bolagsverket was inconsistent.")
				.withStatus(BAD_GATEWAY)
				.build();
		}
	}

}
