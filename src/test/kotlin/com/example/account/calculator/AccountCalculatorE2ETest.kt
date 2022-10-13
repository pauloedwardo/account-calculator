package com.example.account.calculator

import com.example.account.calculator.model.Account
import com.github.fppt.jedismock.RedisServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import redis.clients.jedis.Jedis

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test-e2e")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountCalculatorE2ETest {

    private val redis = RedisServer.newRedisServer(6379)

    private val restTemplate = TestRestTemplate()

    @LocalServerPort
    val port: Int = 0

    @BeforeEach
    fun setup() {
        redis.start()
    }

    @AfterEach
    fun cleanup() = redis.stop()

    @Test
    fun `should create account with balance updated`() {
        val response = restTemplate.postForEntity(
            createURLWithPort("/accounts"),
            buildAccount(),
            Account::class.java
        )

        assertNotNull(response)
        assertEquals(HttpStatus.CREATED.value(), response.statusCode.value())
        assertNotNull(response.body)
        response.body?.let {
            assertNotNull(it.id)
            assertEquals(40, it.balance)
        }

        val jedis = Jedis(redis.host, redis.bindPort)
        val keys = jedis.keys("zipcode-account-cache::18013004")
        assertTrue(keys.size == 1)
        assertTrue(keys.contains("zipcode-account-cache::18013004"))
    }

    private fun createURLWithPort(uri: String): String {
        return "http://localhost:$port$uri"
    }

    private fun buildAccount(): Account = Account(
        null,
        "Account-test",
        "123456789",
        20,
        "18013004"
    )
}