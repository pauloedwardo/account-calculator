package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.repository.AccountRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AccountServiceTest {

    @MockK
    lateinit var accountRepository: AccountRepository

    @MockK
    lateinit var updateBalanceService: UpdateBalanceService

    @InjectMockKs
    lateinit var accountService: AccountService

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should get all accounts successful`() {
        every { accountRepository.findAll() } returns listOf(buildAccount())

        val result = accountService.getAll()

        assertNotNull(result)
        assertTrue(result.isNotEmpty())
        verify(exactly = 1) { accountRepository.findAll() }
    }

    @Test
    fun `should create account successful`() {
        val account = buildAccount()

        every { updateBalanceService.execute(account) } returns account
        every { accountRepository.save(account) } returns account

        val result = accountService.create(account)

        assertNotNull(result)
        verify (exactly = 1) { updateBalanceService.execute(account) }
        verify (exactly = 1) { accountRepository.save(account) }
    }

    private fun buildAccount(): Account = Account(
        "q1w2e3er4t5",
        "Account-test",
        "123456789",
        20,
        "18103540"
    )
}