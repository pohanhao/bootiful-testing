package com.example.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import reactor.test.StepVerifier;

@AutoConfigureStubRunner(
        ids = "com.example:producer:+:8080",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
//@AutoConfigureWireMock(port = 8080)
@SpringBootTest
public class ConsumerApplicationTests {

    @Autowired
    private ReservationClient client;

    @Test
    public void contextLoads() {

		/*WireMock.stubFor(WireMock.get(
			WireMock.urlEqualTo("/reservations"))
			.willReturn(WireMock
				.aResponse()
				.withBody("[   {\"id\" : \"1\" , \"reservationName\":\"Jane\"}  ]")
				.withStatus(HttpStatus.OK.value())
				.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			));*/

        StepVerifier
                .create(this.client.getAllReservations())
                .expectNextMatches(reservation ->
                        reservation.getId().equalsIgnoreCase("1") &&
                                reservation.getName().equalsIgnoreCase("Jane"))
                .verifyComplete();

    }

}