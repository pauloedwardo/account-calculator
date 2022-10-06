package com.example.account.calculator.service

import com.example.account.calculator.model.Account
import com.example.account.calculator.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class UpdateBalanceService(val repository: AccountRepository) {

    fun execute(account: Account) {
        print("zip code received ${account.zipCode}")

        //chamar API

        //calcular

    }
}