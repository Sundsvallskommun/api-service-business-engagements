package se.sundsvall.businessengagements.service.mapper.ssbten;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import se.bolagsverket.schema.ssbt.metadata.Datakonsument;

@ExtendWith(SoftAssertionsExtension.class)
class DatakonsumentMapperTest {

	DatakonsumentMapper mapper = new DatakonsumentMapper();

	@Test
	void createDatakonsument(final SoftAssertions softly) {
		final Datakonsument datakonsument = mapper.createDatakonsument();

		softly.assertThat(datakonsument.getService().getServiceNamn()).isEqualTo("BUSENGBV");
		softly.assertThat(datakonsument.getPartId().getOrganisationsnummer()).isEqualTo("2120002411");
		softly.assertThat(datakonsument.getPartNamn()).isEqualTo("SundsvallsKommun");
	}

}
