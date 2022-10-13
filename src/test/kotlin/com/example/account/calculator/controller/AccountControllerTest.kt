package com.example.account.calculator.controller

import com.example.account.calculator.model.Account
import com.example.account.calculator.repository.AccountRepository
import com.example.account.calculator.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(value = [AccountController::class])
class AccountControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockkBean
    lateinit var accountService: AccountService

    @MockkBean
    lateinit var accountRepository: AccountRepository

    @Test
    fun `should create account successful` () {
        val account = buildAccount()

        every { accountService.create(account) } returns account

        val result = mockMvc.perform(post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(buildAccount())))
            .andReturn()

        assertEquals(HttpStatus.CREATED.value(), result.response.status)
        assertTrue(result.response.contentAsString.isNotEmpty())
        verify(exactly = 1) { accountService.create(account) }
    }

    @Test
    fun `should get accounts successful` () {
        val accounts = listOf(buildAccount(), buildAccount())

        every { accountService.getAll() } returns accounts

        val result = mockMvc.perform(get("/accounts")).andReturn()

        assertEquals(HttpStatus.OK.value(), result.response.status)
        assertTrue(result.response.contentAsString.isNotEmpty())
        verify(exactly = 1) { accountService.getAll() }
    }

    private fun toJson(account: Account): String {
        try {
            return objectMapper.writeValueAsString(account)
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

    private fun buildAccount(): Account = Account(
        "q1w2e3er4t5",
        "Account-test",
        "123456789",
        20,
        "18103540"
    )
}