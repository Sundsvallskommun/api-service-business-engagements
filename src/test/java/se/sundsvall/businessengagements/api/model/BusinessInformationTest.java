package se.sundsvall.businessengagements.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import java.time.LocalDate;
import java.util.Random;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BusinessInformationTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void testBean() {
		MatcherAssert.assertThat(BusinessInformation.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var companyName = "Sundsvalls kommun";
		final var legalForm = LegalForm.builder().build();
		final var address = Address.builder().build();
		final var emailAddress = "someEmail";
		final var phoneNumber = "somePhoneNumber";
		final var municipality = Municipality.builder().build();
		final var county = County.builder().build();
		final var fiscalYear = FiscalYear.builder().build();
		final var companyForm = CompanyForm.builder().build();
		final var companyRegistrationTime = LocalDate.now().minusYears(2);
		final var liquidationInformation = LiquidationInformation.builder().build();
		final var deregistrationDate = LocalDate.now().minusYears(1);
		final var companyLocation = CompanyLocation.builder().build();
		final var businessSignatory = "someBusinessSignatory";
		final var companyDescription = "someCompanyDescription";
		final var sharesInformation = SharesInformation.builder().build();
		final var errorInformation = ErrorInformation.builder().build();

		final var businessInformation = BusinessInformation.builder()
			.withCompanyName(companyName)
			.withLegalForm(legalForm)
			.withAddress(address)
			.withEmailAddress(emailAddress)
			.withPhoneNumber(phoneNumber)
			.withMunicipality(municipality)
			.withCounty(county)
			.withFiscalYear(fiscalYear)
			.withCompanyForm(companyForm)
			.withCompanyRegistrationTime(companyRegistrationTime)
			.withLiquidationInformation(liquidationInformation)
			.withDeregistrationDate(deregistrationDate)
			.withCompanyLocation(companyLocation)
			.withBusinessSignatory(businessSignatory)
			.withCompanyDescription(companyDescription)
			.withSharesInformation(sharesInformation)
			.withErrorInformation(errorInformation)
			.build();

		assertThat(businessInformation).hasNoNullFieldsOrProperties();
		assertThat(businessInformation.getCompanyName()).isEqualTo(companyName);
		assertThat(businessInformation.getLegalForm()).isEqualTo(legalForm);
		assertThat(businessInformation.getAddress()).isEqualTo(address);
		assertThat(businessInformation.getEmailAddress()).isEqualTo(emailAddress);
		assertThat(businessInformation.getPhoneNumber()).isEqualTo(phoneNumber);
		assertThat(businessInformation.getMunicipality()).isEqualTo(municipality);
		assertThat(businessInformation.getCounty()).isEqualTo(county);
		assertThat(businessInformation.getFiscalYear()).isEqualTo(fiscalYear);
		assertThat(businessInformation.getCompanyForm()).isEqualTo(companyForm);
		assertThat(businessInformation.getCompanyRegistrationTime()).isEqualTo(companyRegistrationTime);
		assertThat(businessInformation.getLiquidationInformation()).isEqualTo(liquidationInformation);
		assertThat(businessInformation.getDeregistrationDate()).isEqualTo(deregistrationDate);
		assertThat(businessInformation.getCompanyLocation()).isEqualTo(companyLocation);
		assertThat(businessInformation.getBusinessSignatory()).isEqualTo(businessSignatory);
		assertThat(businessInformation.getCompanyDescription()).isEqualTo(companyDescription);
		assertThat(businessInformation.getSharesInformation()).isEqualTo(sharesInformation);
		assertThat(businessInformation.getErrorInformation()).isEqualTo(errorInformation);

	}

	@Test
	void addErrorInformation() {
		final var businessInformation = BusinessInformation.builder().build();
		final var errorCode = "errorCode";
		final var errorDescription = "errorDescription";
		businessInformation.addErrorInformation(errorCode, errorDescription);
		assertThat(businessInformation.getErrorInformation()).isNotNull();
		assertThat(businessInformation.getErrorInformation().getHasErrors()).isTrue();
		assertThat(businessInformation.getErrorInformation().getErrorDescriptions()).hasSize(1);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(BusinessInformation.builder().build()).hasAllNullFieldsOrProperties();
	}

}
