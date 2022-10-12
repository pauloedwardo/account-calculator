package com.example.account.calculator.controller

import com.example.account.calculator.model.Account
import com.example.account.calculator.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("accounts")
class AccountController(val accountService: AccountService) {

    @PostMapping
    fun create(@RequestBody account: Account): ResponseEntity<Any> {
        val createdAccount = accountService.create(account)

        return ResponseEntity<Any>(createdAccount, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAll() = accountService.getAll()

}