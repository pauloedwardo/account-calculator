package com.example.account.calculator.repository

import com.example.account.calculator.model.Account
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles

@DataMongoTest
@ActiveProfiles("test")
class AccountRepositoryIntegrationTest {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Test
    fun `should save account successful`()  {
        val account = buildAccount()

        val result = accountRepository.save(account)

        assertNotNull(result.id)
    }

    @Test
    fun `should find all accounts successful`()  {
        accountRepository.save(buildAccount())
        accountRepository.save(buildAccount())

        val result = accountRepository.findAll()

        assertTrue(result.size == 2)
        assertNotNull(result[0].id)
        assertNotNull(result[1].id)
    }

    private fun buildAccount(): Account = Account(
        null,
        "Account-test",
        "123456789",
        20,
        "18103540"
    )
}