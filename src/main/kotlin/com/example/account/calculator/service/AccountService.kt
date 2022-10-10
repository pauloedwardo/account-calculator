package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService(val repository: AccountRepository, val updateBalanceService: UpdateBalanceService) {

    fun get(document: String): Account {
        val account = repository.findByDocument(document).get()

        val updatedAccount = updateBalanceService.execute(account)

        save(updatedAccount)

        return updatedAccount
    }

    fun getAll(): List<Account> = repository.findAll()

    fun save(account: Account) {
        repository.save(account)
    }
}