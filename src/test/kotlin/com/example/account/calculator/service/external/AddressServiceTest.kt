package com.example.account.calculator.service.external

import com.example.account.calculator.model.Address
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddressServiceTest {

    @MockK
    lateinit var addressClient: AddressClient

    @InjectMockKs
    lateinit var addressService: AddressService

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should get address successful`() {
        val address = buildAddress()

        every { addressClient.getAddress("18013004") } returns address

        val result = addressService.get("18013004")

        assertNotNull(result)
        verify(exactly = 1) { addressClient.getAddress("18013004") }
    }

    @Test
    fun `should get address null due an exception`() {
        val address = buildAddress()

        every { addressClient.getAddress("18013004") } throws Exception("Address client timeout")

        val result = addressService.get("18013004")

        assertNull(result)
        verify(exactly = 1) { addressClient.getAddress("18013004") }
    }

    private fun buildAddress(): Address = Address(
        "18013004",
        "Avenida Sao Paulo",
        "de 1632/1633 ao fim",
        "Além Ponte",
        "Sorocaba",
        "SP",
        "3552205",
        "6695",
        "15",
        "7145"
    )
}