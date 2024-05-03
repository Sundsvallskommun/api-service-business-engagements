package apptest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import se.sundsvall.businessengagements.BusinessEngagements;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

@WireMockAppTestSuite(files = "classpath:/BusinessInformationIT/", classes = BusinessEngagements.class)
class BusinessInformationIT extends AbstractAppTest {

	@BeforeEach
	public void setup() {
		CommonStubs.stubAccessToken();
	}

	@Test
	void test1_fetchingBusinesInformation() throws Exception {
		String partyId = "fb2f0290-3820-11ed-a261-0242ac120003";
		setupCall()
			.withServicePath("/information/" + partyId + "?organizationName=Some%20Organization")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_fetchBusinessInformation_shouldOnlyExcludeMissingInformation() {
		String partyId = "fb2f0290-3820-11ed-a261-0242ac120003";
		setupCall()
			.withServicePath("/information/" + partyId + "?organizationName=Some%20Organization")
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}
}
