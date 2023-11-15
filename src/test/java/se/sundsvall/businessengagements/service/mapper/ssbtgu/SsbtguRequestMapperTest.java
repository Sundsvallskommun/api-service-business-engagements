package se.sundsvall.businessengagements.service.mapper.ssbtgu;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.zalando.problem.ThrowableProblem;

import se.bolagsverket.schema.ssbt.metadata.Anvandningsomrade;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgiftId;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaran;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaranDetaljer;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaranMetadata;

@ExtendWith(SoftAssertionsExtension.class)
class SsbtguRequestMapperTest {

	private SsbtguRequestMapper mapper = new SsbtguRequestMapper();

	@Test
	void testBasicInformationIsPresent(final SoftAssertions softly) {
		GrundlaggandeUppgifterBegaran req = mapper.createGrundlaggandeUppgifterBegaran("orgnumber", "orgname");
		GrundlaggandeUppgifterBegaranMetadata metaData = req.getGrundlaggandeUppgifterBegaranMetadata();

		softly.assertThat(metaData.getDatakonsument().getPartId().getOrganisationsnummer()).isEqualTo("2120002411");
		softly.assertThat(metaData.getDatakonsument().getService().getServiceNamn()).isEqualTo("BUSENGBV");
		softly.assertThat(metaData.getAnvandare().getPartId().getOrganisationsnummer()).isEqualTo("orgnumber");
		softly.assertThat(metaData.getAnvandare().getPartNamn()).isEqualTo("orgname");
		softly.assertThat(metaData.getAnvandningsomrade()).isEqualTo(Anvandningsomrade.INDIREKT_ATERANVANDNING);

		GrundlaggandeUppgifterBegaranDetaljer details = req.getGrundlaggandeUppgifterBegaranDetaljer();
		softly.assertThat(details.getForetagId().getPersonIdentitetsbeteckning().getOrganisationsnummer()).isEqualTo("orgnumber");
	}

	@Test
	void testBasicInformationIsPresentWithOrgNameAsNull(final SoftAssertions softly) {
		final var req = mapper.createGrundlaggandeUppgifterBegaran("orgnumber", null);
		final var metaData = req.getGrundlaggandeUppgifterBegaranMetadata();

		softly.assertThat(metaData.getDatakonsument().getPartId().getOrganisationsnummer()).isEqualTo("2120002411");
		softly.assertThat(metaData.getDatakonsument().getService().getServiceNamn()).isEqualTo("BUSENGBV");
		softly.assertThat(metaData.getAnvandare().getPartId().getOrganisationsnummer()).isEqualTo("orgnumber");
		softly.assertThat(metaData.getAnvandare().getPartNamn()).isEqualTo("N/A");
		softly.assertThat(metaData.getAnvandningsomrade()).isEqualTo(Anvandningsomrade.INDIREKT_ATERANVANDNING);

		final var details = req.getGrundlaggandeUppgifterBegaranDetaljer();
		softly.assertThat(details.getForetagId().getPersonIdentitetsbeteckning().getOrganisationsnummer()).isEqualTo("orgnumber");
	}


	@Test
	void testRequestContainsWantedUppgiftIds(final SoftAssertions softly) {
		GrundlaggandeUppgifterBegaranDetaljer details = mapper.createGrundlaggandeUppgifterBegaran("orgnumber", "orgname").getGrundlaggandeUppgifterBegaranDetaljer();

		List<String> ids = new ArrayList<>(details.getGrundlaggandeUppgiftIds().stream().map(GrundlaggandeUppgiftId::value).toList());

		for (String id : ids) {
			softly.assertThat(mapper.informationIds).contains(id);
		}
	}

	@Test
	void testXmlGregorianCalendarIsInitialized(final SoftAssertions softly) {
		XMLGregorianCalendar xmlCal = mapper.createXmlGregorianCalendarNow();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dateTimeFromCalendar = LocalDateTime.of(xmlCal.getYear(), xmlCal.getMonth(), xmlCal.getDay(), xmlCal.getHour(), xmlCal.getMinute(), xmlCal.getSecond());
		softly.assertThat(dateTimeFromCalendar.isBefore(now)).isTrue();
	}

	@Test
	void testXmlGregorianCalendarIsInitialized2(final SoftAssertions softly) {
		try (var factory = Mockito.mockStatic(DatatypeFactory.class)) {
			factory.when(DatatypeFactory::newInstance).thenThrow(new DatatypeConfigurationException());
			assertThatExceptionOfType(ThrowableProblem.class).isThrownBy(() -> mapper.createXmlGregorianCalendarNow())
				.withMessage("Error while creating request towards Bolagsverket");
		}
	}

}
