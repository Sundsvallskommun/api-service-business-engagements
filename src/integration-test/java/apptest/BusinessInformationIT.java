package apptest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import se.sundsvall.businessengagements.Application;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;

@WireMockAppTestSuite(files = "classpath:/BusinessInformationIT/", classes = Application.class)
class BusinessInformationIT extends AbstractAppTest {

	private static final String PARTY_ID = "fb2f0290-3820-11ed-a261-0242ac120003";

	private static final String PATH = "/2281/information/" + PARTY_ID + "?organizationName=Some%20Organization";

	@BeforeEach
	public void setup() {
		CommonStubs.stubAccessToken();
	}

	@Test
	void test1_fetchingBusinesInformation() {
		setupCall()
			.withServicePath(PATH)
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_fetchBusinessInformation_shouldOnlyExcludeMissingInformation() {
		setupCall()
			.withServicePath(PATH)
			.withHttpMethod(HttpMethod.GET)
			.withExpectedResponseStatus(HttpStatus.OK)
			.withExpectedResponse("expected.json")
			.sendRequestAndVerifyResponse();
	}

}
