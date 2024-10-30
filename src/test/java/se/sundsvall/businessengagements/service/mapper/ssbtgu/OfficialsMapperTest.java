package se.sundsvall.businessengagements.service.mapper.ssbtgu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import se.sundsvall.businessengagements.api.model.Official;
import se.sundsvall.dept44.test.annotation.resource.Load;
import se.sundsvall.dept44.test.extension.ResourceLoaderExtension;

@ExtendWith({
	SoftAssertionsExtension.class, ResourceLoaderExtension.class
})
class OfficialsMapperTest {

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	String regularOfficialsString = """
		styrelseledamot,ordförande
		198000000010 Larsson, Kent, SUNDSVALL
		styrelseledamot
		198001011234 Karlsson, Kristina, STOCKHOLM
		198000000011 Larsson, Nils, SUNDSVALL
		styrelsesuppleant
		19870310 Holt, James, GÖTEBORG
		revisor
		5560000010, Revisionsbyrån AB, STOCKHOLM
		huvudansvarig revisor
		198000000012 Westling, Amandus, LUDVIKA
		""";

	String duplicateRoleString = """
		styrelseledamot,ordförande
		198000000010 Larsson, Kent, SUNDSVALL
		styrelsesuppleant
		198000000010 Larsson, Kent, SUNDSVALL
		""";

	@Test
	void testMapOfficials(final SoftAssertions softly,
		@Load(value = "unit-test-files/officials-regular.json", as = Load.ResourceType.STRING) String expectedList) throws JsonProcessingException {
		List<Official> officialList = OfficialsMapper.mapOfficials(regularOfficialsString);
		String wanted = gson.toJson(officialList);
		ObjectMapper mapper = new ObjectMapper();
		assertEquals(mapper.readTree(wanted), mapper.readTree(expectedList));
	}

	@Test
	void testMapOfficialsWithDuplicateRoles(final SoftAssertions softly,
		@Load(value = "unit-test-files/officials-duplicate.json", as = Load.ResourceType.STRING) String expectedList) throws JsonProcessingException {
		List<Official> officialList = OfficialsMapper.mapOfficials(duplicateRoleString);
		String wanted = gson.toJson(officialList);
		ObjectMapper mapper = new ObjectMapper();
		assertEquals(mapper.readTree(wanted), mapper.readTree(expectedList));
	}

}
