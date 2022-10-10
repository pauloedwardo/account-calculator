package com.example.account.calculator

import com.example.account.calculator.model.Account
import com.example.account.calculator.repository.AccountRepository
import com.example.account.calculator.service.AccountService
import com.example.account.calculator.service.UpdateBalanceService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

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
    fun `should get account by document successful` () {
        every { accountRepository.findByDocument(any()) } returns buildOptionalAccount()
        every { updateBalanceService.execute(any()) } returns buildAccount()
        every { accountRepository.save(any()) } returns buildAccount()

        val account = accountService.get("123456789")

        assertNotNull(account)
        assertEquals(20, account.balance)
        verify (exactly = 1) { accountRepository.findByDocument(any()) }
        verify (exactly = 1) { updateBalanceService.execute(any()) }
        verify (exactly = 1) { accountRepository.save(any()) }
    }

    companion object {
        fun buildOptionalAccount(): Optional<Account> = Optional.of(Account(
            "q1w2e3er4t5",
            "Account-test",
            "123456789",
            10,
            "18103540"
        ))

        fun buildAccount(): Account = Account(
            "q1w2e3er4t5",
            "Account-test",
            "123456789",
            20,
            "18103540"
        )
    }
}