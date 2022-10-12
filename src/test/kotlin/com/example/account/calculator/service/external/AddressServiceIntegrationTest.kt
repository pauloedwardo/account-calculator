package com.example.account.calculator.service.external

import com.github.fppt.jedismock.RedisServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.givenThat
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import redis.clients.jedis.Jedis

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@WireMockTest(httpPort = 8090)
@ActiveProfiles("test")
class AddressServiceIntegrationTest {

    private val redis = RedisServer.newRedisServer(6379)

    @Autowired
    lateinit var addressService: AddressService

    @BeforeEach
    fun setup() {
        redis.start()
    }

    @AfterEach
    fun cleanup() = redis.stop()

    @Test
    fun `should cache zipCode successful`() {

         givenThat(get(urlEqualTo("/ws/18013004/json"))
             .willReturn(aResponse()
                 .withStatus(HttpStatus.OK.value())
                 .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                 .withBodyFile("AddressSuccessResponse.json")
             )
        )

        val jedis = Jedis(redis.host, redis.bindPort)

        val result = addressService.get("18013004")

        assertNotNull(result)
        val keys = jedis.keys("zipcode-account-cache::18013004")
        assertTrue(keys.size == 1)
        assertTrue(keys.contains("zipcode-account-cache::18013004"))
    }

    @Test
    fun `should not cache zipCode due exception`() {

        givenThat(get(urlEqualTo("/ws/11111111/json"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile("")
            )
        )

        val jedis = Jedis(redis.host, redis.bindPort)

        val result = addressService.get("11111111")

        assertNull(result)
        val keys = jedis.keys("zipcode-account-cache::11111111")
        assertTrue(keys.size == 0)
        assertFalse(keys.contains("zipcode-account-cache::11111111"))
    }
}