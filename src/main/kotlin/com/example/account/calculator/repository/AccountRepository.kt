package com.example.account.calculator.repository

import com.example.account.calculator.model.Account
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface AccountRepository : MongoRepository<Account, String> {

}