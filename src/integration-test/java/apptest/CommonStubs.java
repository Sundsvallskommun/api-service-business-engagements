package apptest;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CommonStubs {

	public static void stubAccessToken(final WireMockServer wireMockServer) {
		stubAccessToken(wireMockServer, "/token");
	}

	public static void stubAccessToken(final WireMockServer wireMockServer, final String url) {
		wireMockServer.stubFor(post(url)
			.willReturn(aResponse()
				.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.withBody("{\"access_token\":\"abc123\",\"not-before-policy\":0,\"session_state\":\"88bbf486\",\"token_type\": \"bearer\"}")));
	}

}
