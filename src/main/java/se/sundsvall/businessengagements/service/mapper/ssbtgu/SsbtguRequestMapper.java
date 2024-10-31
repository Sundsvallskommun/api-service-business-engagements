package se.sundsvall.businessengagements.service.mapper.ssbtgu;

import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.SERVICE_NAME;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.SUNDSVALLS_KOMMUN;
import static se.sundsvall.businessengagements.service.BusinessEngagementsService.SUNDSVALL_MUNICIPALITY_ORGANIZATION_NUMBER;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;

import se.sundsvall.dept44.requestid.RequestId;

import se.bolagsverket.schema.ssbt.foretag.PersonIdentitetsbeteckning;
import se.bolagsverket.schema.ssbt.metadata.Anvandningsomrade;
import se.bolagsverket.schema.ssbt.metadata.Datakonsument;
import se.bolagsverket.schema.ssbt.metadata.Part;
import se.bolagsverket.schema.ssbt.metadata.PartId;
import se.bolagsverket.schema.ssbt.metadata.Service;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.ForetagId;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgiftId;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaran;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaranDetaljer;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterBegaranMetadata;

@Component
public class SsbtguRequestMapper {

	static final List<String> informationIds = List.of(
		"UD0001",   // Company name
		"UD0002",   // Juridic form
		"UD0003",   // Postal address
		"UD0004",   // E-mail
		"UD0006",   // Phone number
		"UD0011",   // Municipality where the company is located
		"UD0012",   // County where the company is located
		"UD0014",   // Fiscal year
		"UD0025",   // Company form
		"UD0026",   // Company registration time
		"UD0027",   // Business status/liquidation
		"UD0028",   // Date when/if the company was deregistered
		"UD0040",   // Company location
		"UD0045",   // Signatory information
		"UD0046",   // Company operation description
		"UD0048"    // Shares info
	);

	public GrundlaggandeUppgifterBegaran createGrundlaggandeUppgifterBegaran(final String orgNumber, String orgName) {
		if (StringUtils.isBlank(orgName)) {
			orgName = "N/A";
		}
		return new GrundlaggandeUppgifterBegaran()
			.withGrundlaggandeUppgifterBegaranMetadata(createGrundlaggandeUppgifterBegaranMetadata(orgNumber, orgName))
			.withGrundlaggandeUppgifterBegaranDetaljer(createGrundlaggandeUppgifterBegaranDetaljer(orgNumber))
			.withSchemaVersion("2.5.0");

	}

	GrundlaggandeUppgifterBegaranMetadata createGrundlaggandeUppgifterBegaranMetadata(String orgNumber, String orgName) {
		return new GrundlaggandeUppgifterBegaranMetadata()
			.withMeddelandeId(UUID.randomUUID().toString())
			.withTransaktionId(RequestId.get())
			.withTidstampel(createXmlGregorianCalendarNow())
			.withDatakonsument(new Datakonsument()
				.withPartId(new PartId()
					.withOrganisationsnummer(SUNDSVALL_MUNICIPALITY_ORGANIZATION_NUMBER))
				.withPartNamn(SUNDSVALLS_KOMMUN)
				.withService(new Service()
					.withServiceNamn(SERVICE_NAME)))
			.withAnvandare(new Part()
				.withPartId(new PartId()
					.withOrganisationsnummer(orgNumber))
				.withPartNamn(orgName))
			.withAnvandningsomrade(Anvandningsomrade.INDIREKT_ATERANVANDNING);

	}

	GrundlaggandeUppgifterBegaranDetaljer createGrundlaggandeUppgifterBegaranDetaljer(String orgNumber) {
		return new GrundlaggandeUppgifterBegaranDetaljer()
			.withForetagId(new ForetagId()
				.withPersonIdentitetsbeteckning(new PersonIdentitetsbeteckning()
					.withOrganisationsnummer(orgNumber)))
			.withGrundlaggandeUppgiftIds(informationIds.stream().map(GrundlaggandeUppgiftId::fromValue).toList());
	}

	XMLGregorianCalendar createXmlGregorianCalendarNow() {
		try {
			var zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
			var cal = GregorianCalendar.from(zonedDateTime);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (DatatypeConfigurationException e) {
			// Should never happen.
			throw Problem.builder()
				.withTitle("Error while creating request towards Bolagsverket")
				.withStatus(INTERNAL_SERVER_ERROR)
				.withDetail(e.getMessage())
				.build();
		}
	}

}
