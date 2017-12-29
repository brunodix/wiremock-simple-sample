package test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.Options;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class AuthenticatedMockRest {

    public static void main(String[] args) {
        new AuthenticatedMockRest().start();
    }

    private void start() {
        Options options = wireMockConfig().port(2000).needClientAuth(true);
        WireMockServer wireMockServer = new WireMockServer(options);
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());

        WireMock.reset();

        stubFor(get(urlEqualTo("/my/test"))
                .withBasicAuth("joseph", "climber")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody("{ \"content\": \"ok\"}")));

    }
}
