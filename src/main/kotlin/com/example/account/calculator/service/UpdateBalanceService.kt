package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.service.external.AddressClient
import com.example.account.calculator.service.external.AddressService
import org.springframework.stereotype.Service

@Service
class UpdateBalanceService(val addressService: AddressService) {

    fun execute(account: Account): Account {
        val response = addressService.get(account.zipCode)

        if (response != null && "SP" == response.uf) {
            account.balance = account.balance?.times(2)
        }

        return account
    }
}