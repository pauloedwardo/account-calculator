package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.service.external.AddressService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}
@Service
class UpdateBalanceService(val addressService: AddressService) {

    fun execute(account: Account): Account {
        val response = addressService.get(account.zipCode)

        if (response != null && "SP" == response.uf) {
            logger.info("Updating account balance...")

            account.balance = account.balance?.times(2)
        }

        return account
    }
}