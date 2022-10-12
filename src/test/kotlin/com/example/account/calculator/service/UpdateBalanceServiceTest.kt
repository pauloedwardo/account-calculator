package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.model.Address
import com.example.account.calculator.service.external.AddressService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateBalanceServiceTest {

    @MockK
    lateinit var addressService: AddressService

    @InjectMockKs
    lateinit var updateBalanceService: UpdateBalanceService

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should update balance successful`() {
        val account = buildAccount()

        every { addressService.get(account.zipCode) } returns buildAddress("SP")

        val result = updateBalanceService.execute(account)

        assertEquals(40, result.balance)
        verify(exactly = 1) { addressService.get(account.zipCode) }
    }

    @Test
    fun `should not update balance due address response to be null`() {
        val account = buildAccount()

        every { addressService.get(account.zipCode) } returns null

        val result = updateBalanceService.execute(account)

        assertEquals(20, result.balance)
        verify(exactly = 1) { addressService.get(account.zipCode) }
    }

    @Test
    fun `should not update balance due address uf field doesnt match`() {
        val account = buildAccount()

        every { addressService.get(account.zipCode) } returns buildAddress("RJ")

        val result = updateBalanceService.execute(account)

        assertEquals(20, result.balance)
        verify(exactly = 1) { addressService.get(account.zipCode) }
    }

    private fun buildAccount(): Account = Account(
        "q1w2e3er4t5",
        "Account-test",
        "123456789",
        20,
        "18103540"
    )

    private fun buildAddress(uf: String): Address = Address(
        "18013004",
        "Avenida Sao Paulo",
        "de 1632/1633 ao fim",
        "Além Ponte",
        "Sorocaba",
        uf,
        "3552205",
        "6695",
        "15",
        "7145"
    )
}