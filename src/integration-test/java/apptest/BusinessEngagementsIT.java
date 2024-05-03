package apptest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import se.sundsvall.businessengagements.BusinessEngagements;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
// This suppresses the "(java:S2699)Tests should include assertions" rule.
// Since we are using dept44s AbstractAppTest we don't need to add assertions
// as they are already included in the AbstractAppTest#sendRequestAndVerifyResponse method.
// This also suppresses the "(java:S5976) Similar tests should be grouped in a single Parameterized test" rule.
// Because we are using expected.json files to verify the response, a parameterized test would be messy.
@WireMockAppTestSuite(files = "classpath:/BusinessEngagementsIT/", classes = BusinessEngagements.class)
class BusinessEngagementsIT extends AbstractAppTest {

	@BeforeEach
	public void setup() {
		CommonStubs.stubAccessToken();
	}

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	//Since data is persisted in the H2 we need to reset it in methods that use the same data
	@Test
	void test1_successful() {
		String partyId = "e57e9dec-4132-11ec-973a-0242ac130003";   //For clarity, this is what we match the request on.
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_okResponseButTimeoutFromBolagsverket() {
		String partyId = "e57e9ffe-4132-11ec-973a-0242ac130003";
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test3_noEngagementsShouldReturnNoContent() {
		String partyId = "e57ea0ee-4132-11ec-973a-0242ac130003";
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.NO_CONTENT)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test4_errorResponseFromBolagsverket_shouldReturnError() {
		String partyId = "e57ea1b6-4132-11ec-973a-0242ac130003";
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test5_okResponseButInternalErrorsFromBothBolagsverketAndSkatteverket() {
		String partyId = "e57ea274-4132-11ec-973a-0242ac130003";
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@Test
	void test6_timeoutFromBolagsverket_shouldThrowException() {
		String partyId = "522b52c1-c34d-4f80-b637-29288b08d6dc";
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	/**
	 * Faking a 404 from LegalEntity for "org-no" 198001011234.
	 *
	 */
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	//Since data is persisted in the H2 we need to reset it in methods that use the same data
	@Test
	void test7_missingGuidFromLegalEntity_shouldPopulateStatusDescription() {
		String partyId = "e57e9dec-4132-11ec-973a-0242ac130003";   //For clarity, this is what we match the request on.
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test8_404FromParty_shouldReturn404() {
		String partyId = "6a5c3d04-412d-11ec-973a-0242ac130004";
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.NOT_FOUND)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test9_400FromParty_whenMappingOrganizationId_shouldNotReturnError() {
		String partyId = "65694a6d-5f5d-4bb6-b256-3b81cb419b60";
		setupCall()
			.withServicePath("/engagements/" + partyId + "?personalName=Jane%20Doe&serviceName=Kommunen")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

}
