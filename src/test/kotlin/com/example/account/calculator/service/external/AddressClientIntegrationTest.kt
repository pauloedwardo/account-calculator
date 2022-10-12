package com.example.account.calculator.service.external

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.givenThat
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@ContextConfiguration(classes = [AddressClient::class])
@WireMockTest(httpPort = 8090)
@ActiveProfiles("test")
class AddressClientIntegrationTest {

    @Autowired
    lateinit var addressClient: AddressClient

    @Test
    fun `should get address from api`() {
        val url = "/ws/18013004/json"

        givenThat(get(urlEqualTo(url))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile("AddressSuccessResponse.json")
            )
        )

        val result = addressClient.getAddress("18013004")

        assertNotNull(result)
    }
}
