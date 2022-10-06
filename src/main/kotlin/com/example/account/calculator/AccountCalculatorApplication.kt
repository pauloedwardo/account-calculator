package com.example.account.calculator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class AccountCalculatorApplication

fun main(args: Array<String>) {
	runApplication<AccountCalculatorApplication>(*args)
}
