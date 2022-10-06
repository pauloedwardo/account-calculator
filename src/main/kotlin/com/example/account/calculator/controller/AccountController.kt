package com.example.account.calculator.controller

import com.example.account.calculator.model.Account
import com.example.account.calculator.repository.AccountRepository
import com.example.account.calculator.service.AccountService
import com.example.account.calculator.service.UpdateBalanceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("accounts")
class AccountController(val repository: AccountRepository, val accountService: AccountService) {

    @PostMapping
    fun create(@RequestBody account: Account): ResponseEntity<Account> = ResponseEntity.ok(repository.save(account))

    @GetMapping("{document}")
    fun get(@PathVariable document: String) = accountService.get(document)

    @GetMapping
    fun getAll() = accountService.getAll()

    @PutMapping("{document}")
    fun update(@PathVariable document: String, @RequestBody account: Account): ResponseEntity<Account> {
        val accountOptional = repository.findByDocument(document)
        val toSave = accountOptional.orElseThrow { java.lang.RuntimeException("Account document: $document not found!") }
            .copy(name = account.name, balance = account.balance)
        return ResponseEntity.ok(repository.save(toSave))
    }

    @DeleteMapping("{document}")
    fun delete(@PathVariable document: String) = repository
        .findByDocument(document)
        .ifPresent{ repository.delete(it) }
}