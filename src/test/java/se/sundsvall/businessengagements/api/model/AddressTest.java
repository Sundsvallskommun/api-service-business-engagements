package se.sundsvall.businessengagements.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

class AddressTest {

	@Test
	void testBean() {
		MatcherAssert.assertThat(Address.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {

		final var city = "someCity";
		final var streetName = "someStreetName";
		final var careOf = "someCareOf";
		final var postalCode = "somePostalCode";

		final var address = Address.builder()
			.withCity(city)
			.withPostcode(postalCode)
			.withCareOf(careOf)
			.withStreet(streetName)
			.build();

		assertThat(address).hasNoNullFieldsOrProperties();
		assertThat(address.getCity()).isEqualTo(city);
		assertThat(address.getStreet()).isEqualTo(streetName);
		assertThat(address.getCareOf()).isEqualTo(careOf);
		assertThat(address.getPostcode()).isEqualTo(postalCode);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Address.builder().build()).hasAllNullFieldsOrProperties();
	}

}
