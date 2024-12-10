package se.sundsvall.businessengagements.service;

import org.springframework.stereotype.Service;
import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;
import se.sundsvall.businessengagements.integration.bolagsverket.ssbten.SsbtenIntegration;
import se.sundsvall.businessengagements.service.mapper.ssbten.EngagemangBegaranRequestMapper;
import se.sundsvall.businessengagements.service.mapper.ssbten.EngagemangSvarMapper;

@Service
public class SsbtenService {

	private final SsbtenIntegration ssbtenIntegration;

	private final EngagemangSvarMapper engagemangSvarMapper;

	private final EngagemangBegaranRequestMapper engagemangBegaranRequestMapper;

	public SsbtenService(SsbtenIntegration ssbtenIntegration, EngagemangSvarMapper engagemangSvarMapper, EngagemangBegaranRequestMapper engagemangBegaranRequestMapper) {
		this.ssbtenIntegration = ssbtenIntegration;
		this.engagemangSvarMapper = engagemangSvarMapper;
		this.engagemangBegaranRequestMapper = engagemangBegaranRequestMapper;
	}

	public BusinessEngagementsResponse getBusinessEngagements(BusinessEngagementsRequestDto requestDto) {
		// Create request object for Bolagsverket
		var engagemangBegaranRequest = engagemangBegaranRequestMapper.createEngagemangBegaranRequest(requestDto);

		// Get the response
		var engagemangSvar = ssbtenIntegration.callBolagsverket(engagemangBegaranRequest);

		// Map it to our response and return
		return engagemangSvarMapper.mapBolagsverketResponse(engagemangSvar);
	}

}
