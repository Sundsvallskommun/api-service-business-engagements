package se.sundsvall.businessengagements.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.sundsvall.businessengagements.api.model.BusinessEngagementsResponse;
import se.sundsvall.businessengagements.domain.dto.BusinessEngagementsRequestDto;
import se.sundsvall.businessengagements.integration.bolagsverket.ssbten.SsbtenIntegration;
import se.sundsvall.businessengagements.service.mapper.ssbten.EngagemangBegaranRequestMapper;
import se.sundsvall.businessengagements.service.mapper.ssbten.EngagemangSvarMapper;

import se.bolagsverket.schema.ssbten.engagemang.EngagemangBegaran;
import se.bolagsverket.schema.ssbten.engagemang.EngagemangSvar;

@ExtendWith({
	MockitoExtension.class, SoftAssertionsExtension.class
})
class SsbtenServiceTest {

	@Mock
	private SsbtenIntegration mockSsbtenIntegration;

	@Mock
	private EngagemangSvarMapper mockEngagemangSvarMapper;

	@Mock
	private EngagemangBegaranRequestMapper mockEngagemangBegaranRequestMapper;

	@InjectMocks
	private SsbtenService service;

	@Test
	void testGetBusinessEngagements(final SoftAssertions softly) {
		when(mockEngagemangBegaranRequestMapper.createEngagemangBegaranRequest(any(BusinessEngagementsRequestDto.class))).thenReturn(new EngagemangBegaran());
		when(mockSsbtenIntegration.callBolagsverket(any(EngagemangBegaran.class))).thenReturn(new EngagemangSvar());
		when(mockEngagemangSvarMapper.mapBolagsverketResponse(any(EngagemangSvar.class))).thenReturn(new BusinessEngagementsResponse());

		softly.assertThat(service.getBusinessEngagements(new BusinessEngagementsRequestDto())).isNotNull();

		verify(mockEngagemangBegaranRequestMapper, times(1)).createEngagemangBegaranRequest(any(BusinessEngagementsRequestDto.class));
		verify(mockSsbtenIntegration, times(1)).callBolagsverket(any(EngagemangBegaran.class));
		verify(mockEngagemangSvarMapper, times(1)).mapBolagsverketResponse(any(EngagemangSvar.class));
	}

}
