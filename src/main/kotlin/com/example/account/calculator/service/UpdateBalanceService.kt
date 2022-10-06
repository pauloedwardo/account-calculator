package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.service.external.AddressClient
import org.springframework.stereotype.Service

@Service
class UpdateBalanceService(val addressClient: AddressClient) {

    fun execute(account: Account) {
        val response = addressClient.getAddress(account.zipCode)

    }
}