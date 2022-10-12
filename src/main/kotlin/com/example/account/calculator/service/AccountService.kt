package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.repository.AccountRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}
@Service
class AccountService(val repository: AccountRepository,
                     val updateBalanceService: UpdateBalanceService) {

    fun getAll(): List<Account> = repository.findAll()

    fun create(account: Account): Account {
        logger.info("creating account $account")

        val updatedAccount = updateBalanceService.execute(account)

        return repository.save(updatedAccount)
    }
}