package se.sundsvall.businessengagements.service.mapper.ssbtgu;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

import se.sundsvall.businessengagements.api.model.Address;
import se.sundsvall.businessengagements.api.model.BusinessInformation;
import se.sundsvall.businessengagements.api.model.CompanyForm;
import se.sundsvall.businessengagements.api.model.CompanyLocation;
import se.sundsvall.businessengagements.api.model.County;
import se.sundsvall.businessengagements.api.model.FiscalYear;
import se.sundsvall.businessengagements.api.model.LegalForm;
import se.sundsvall.businessengagements.api.model.LiquidationInformation;
import se.sundsvall.businessengagements.api.model.Municipality;
import se.sundsvall.businessengagements.api.model.SharesInformation;

import se.bolagsverket.schema.ssbt.fel.Fel;
import se.bolagsverket.schema.ssbt.foretag.Avvecklingsforfarande;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterSvar;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.GrundlaggandeUppgifterSvarDetaljer;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0001;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0002;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0003;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0004;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0006;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0011;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0012;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0014;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0025;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0026;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0027;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0028;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0040;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0045;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0046;
import se.bolagsverket.schema.ssbtgu.v2.grundlaggandeuppgifter.UD0048;

@Component
public class SsbtguResponseMapper {

	public BusinessInformation mapGrundlaggandeUppgifterSvar(GrundlaggandeUppgifterSvar response) {

		GrundlaggandeUppgifterSvarDetaljer details = response.getGrundlaggandeUppgifterSvarDetaljer();

		BusinessInformation businessInformation = new BusinessInformation();

		UD0001 ud0001 = details.getUD0001();
		mapUD0001CompanyName(ud0001, businessInformation);

		UD0002 ud0002 = details.getUD0002();
		mapUD0002LegalForm(ud0002, businessInformation);

		UD0003 ud0003 = details.getUD0003();
		mapUD0003Address(ud0003, businessInformation);

		UD0004 ud0004 = details.getUD0004();
		mapUD0004EmailAddress(ud0004, businessInformation);

		UD0006 ud0006 = details.getUD0006();
		mapUD0006PhoneNumber(ud0006, businessInformation);

		UD0011 ud0011 = details.getUD0011();
		mapUD0011Municipality(ud0011, businessInformation);

		UD0012 ud0012 = details.getUD0012();
		mapUD0012County(ud0012, businessInformation);

		UD0014 ud0014 = details.getUD0014();
		mapUD0014FiscalYear(ud0014, businessInformation);

		UD0025 ud0025 = details.getUD0025();
		mapUD0025CompanyForm(ud0025, businessInformation);

		UD0026 ud0026 = details.getUD0026();
		mapUD0026CompanyRegistrationTime(ud0026, businessInformation);

		UD0027 ud0027 = details.getUD0027();
		mapUD0027LiquidationInformation(ud0027, businessInformation);

		UD0028 ud0028 = details.getUD0028();
		mapUD0028DeregistrationDate(ud0028, businessInformation);

		UD0040 ud0040 = details.getUD0040();
		mapUD0040CompanyLocation(ud0040, businessInformation);

		UD0045 ud0045 = details.getUD0045();
		mapUD0045BusinessSignatory(ud0045, businessInformation);

		UD0046 ud0046 = details.getUD0046();
		mapUD0046CompanyDescription(ud0046, businessInformation);

		UD0048 ud0048 = details.getUD0048();
		mapUD0048SharesInformation(ud0048, businessInformation);

		return businessInformation;
	}

	void mapUD0001CompanyName(final UD0001 ud0001, final BusinessInformation businessInformation) {
		Optional.ofNullable(ud0001)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0001::getForetagNamn)
			.ifPresent(businessInformation::setCompanyName);
	}

	void mapUD0002LegalForm(UD0002 ud0002, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0002)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0002::getJuridiskForm)
			.ifPresent(legalForm -> businessInformation.setLegalForm(LegalForm.builder()
				.withLegalFormDescription(legalForm.getJuridiskFormBeskrivning())
				.withLegalFormCode(legalForm.getJuridiskFormKod())
				.build()));
	}

	void mapUD0003Address(UD0003 ud0003, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0003)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0003::getPostadress)
			.ifPresent(address -> businessInformation.setAddress(Address.builder()
				.withCareOf(address.getCOAdress())
				.withCity(address.getPostort())
				.withPostcode(address.getPostnummer())
				.withStreet(address.getUtdelningsadress1())
				.build()));
	}

	void mapUD0004EmailAddress(UD0004 ud0004, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0004)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0004::getEPostAdress)
			.ifPresent(businessInformation::setEmailAddress);
	}

	void mapUD0006PhoneNumber(UD0006 ud0006, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0006)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0006::getTelefonnummer)
			.ifPresent(businessInformation::setPhoneNumber);
	}

	void mapUD0011Municipality(UD0011 ud0011, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0011)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0011::getKommun)
			.ifPresent(municipality -> businessInformation.setMunicipality(Municipality.builder()
				.withMunicipalityName(municipality.getKommunNamn())
				.withMunicipalityCode(municipality.getKommunKod())
				.build()));
	}

	void mapUD0012County(UD0012 ud0012, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0012)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0012::getLan)
			.ifPresent(county -> businessInformation.setCounty(County.builder()
				.withCountyName(county.getLanNamn())
				.withCountyCode(county.getLanKod())
				.build()));
	}

	void mapUD0014FiscalYear(UD0014 ud0014, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0014)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0014::getRakenskapsar)
			.ifPresent(fiscalYear -> businessInformation.setFiscalYear(FiscalYear.builder()
				.withFromDay(fiscalYear.getRakenskapsarFrom().getDag())
				.withFromMonth(fiscalYear.getRakenskapsarFrom().getManad())
				.withToDay(fiscalYear.getRakenskapsarTom().getDag())
				.withToMonth(fiscalYear.getRakenskapsarTom().getManad())
				.build()));
	}

	void mapUD0025CompanyForm(UD0025 ud0025, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0025)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0025::getForetagsform)
			.ifPresent(companyForm -> businessInformation.setCompanyForm(CompanyForm.builder()
				.withCompanyFormCode(companyForm.getForetagsformKod())
				.withCompanyFormDescription(companyForm.getForetagsformBeskrivning())
				.build()));
	}

	void mapUD0026CompanyRegistrationTime(UD0026 ud0026, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0026)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0026::getForetagRegistreratDatum)
			.ifPresent(registrationTime -> businessInformation.setCompanyRegistrationTime(xmlGregorianCalendarToLocalDate(registrationTime)));
	}

	void mapUD0027LiquidationInformation(UD0027 ud0027, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0027)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0027::getAvvecklingsforfarande)
			.ifPresent(liquidation -> businessInformation.setLiquidationInformation(LiquidationInformation.builder()
				.withCancelledLiquidation(mapAvvecklingAvfordAnledning(liquidation))
				.withLiquidationReasons(mapAvvecklingsforfarande(liquidation))
				.build()));
	}

	List<LiquidationInformation.LiquidationReason> mapAvvecklingsforfarande(Avvecklingsforfarande liquidation) {
		if (liquidation.getAvvecklings() != null) {
			return liquidation.getAvvecklings().stream()
				.map(avveckling -> LiquidationInformation.LiquidationReason.builder()
					.withLiquidationCode(avveckling.getAvvecklingKod())
					.withLiquidationDate(xmlGregorianCalendarToLocalDate(avveckling.getAvvecklingDatum()))
					.withLiquidationDescription(avveckling.getAvvecklingBeskrivning())
					.withLiquidationType(avveckling.getTyp().value())
					.build())
				.toList();
		}

		return List.of();
	}

	LiquidationInformation.LiquidationReason mapAvvecklingAvfordAnledning(Avvecklingsforfarande liquidation) {
		if (liquidation.getAvvecklingAvfordAnledning() != null) {
			return LiquidationInformation.LiquidationReason.builder()
				.withLiquidationCode(liquidation.getAvvecklingAvfordAnledning().getAvvecklingKod())
				.withLiquidationDate(xmlGregorianCalendarToLocalDate(liquidation.getAvvecklingAvfordAnledning().getAvvecklingDatum()))
				.withLiquidationDescription(liquidation.getAvvecklingAvfordAnledning().getAvvecklingBeskrivning())
				.withLiquidationType(liquidation.getAvvecklingAvfordAnledning().getTyp().value())
				.build();
		}

		return null;
	}

	void mapUD0028DeregistrationDate(UD0028 ud0028, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0028)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0028::getForetagAvregistreratDatum)
			.ifPresent(deregistrationDate -> businessInformation.setDeregistrationDate(xmlGregorianCalendarToLocalDate(deregistrationDate)));
	}

	void mapUD0040CompanyLocation(UD0040 ud0040, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0040)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0040::getBelagenhetsadress)
			.ifPresent(location -> businessInformation.setCompanyLocation(CompanyLocation.builder()
				.withAddress(Address.builder()
					.withCity(location.getPostort())
					// Concatenate street and number
					.withStreet(location.getAdressomrade())
					.withPostcode(location.getPostnummer())
					.build())
				.build()));
	}

	void mapUD0045BusinessSignatory(UD0045 ud0045, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0045)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0045::getFirmateckningBeskrivning)
			.ifPresent(signatory -> businessInformation.setBusinessSignatory(signatory.replace("\n", " ")));
	}

	void mapUD0046CompanyDescription(UD0046 ud0046, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0046)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0046::getVerksamhetsbeskrivning)
			.ifPresent(description -> businessInformation.setCompanyDescription(description.replace("\n", " ")));
	}

	void mapUD0048SharesInformation(UD0048 ud0048, BusinessInformation businessInformation) {
		Optional.ofNullable(ud0048)
			.filter(ud -> !checkAndPopulatePossibleError(ud.getFel(), businessInformation))
			.map(UD0048::getAktier)
			.ifPresent(shares -> businessInformation.setSharesInformation(SharesInformation.builder()
				.withNumberOfShares(shares.getAktierAntal())
				.withShareCapital(shares.getAktiekapital().getValue().setScale(2, RoundingMode.HALF_EVEN))
				.withShareCurrency(shares.getAktiekapital().getRedovisningsvaluta())
				.withShareTypes(shares.getAktieslags().stream()
					.map(aktieslag -> SharesInformation.ShareType.builder()
						.withLabel(aktieslag.getAktieslagBeteckning())
						.withNumberOfShares(aktieslag.getAktieslagAntal())
						.withVoteValue(aktieslag.getAktieslagRostvarde())
						.build())
					.toList())
				.build()));
	}

	/**
	 * Checks if an error exists and in that case populates the response with information about it.
	 *
	 * @param  error               possible error
	 * @param  businessInformation response object
	 * @return                     if it has an error or not.
	 */
	boolean checkAndPopulatePossibleError(Fel error, BusinessInformation businessInformation) {
		boolean hasError = false;
		if (error != null) {
			hasError = true;
			businessInformation.addErrorInformation(error.getFelKod(), error.getFelBeskrivning());
		}

		return hasError;
	}

	/**
	 * Convert an XMLGregorianCalendar to LocalDate
	 *
	 * @param  calendar to convert
	 * @return          LocalDate if there's a calendar and it's valid, otherwise return null.
	 */
	LocalDate xmlGregorianCalendarToLocalDate(XMLGregorianCalendar calendar) {
		if (calendar != null && calendar.isValid()) {
			return LocalDate.of(calendar.getYear(), calendar.getMonth(), calendar.getDay());
		} else {
			return null;
		}
	}

}
