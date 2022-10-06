package com.example.account.calculator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
@EnableFeignClients
class AccountCalculatorApplication

fun main(args: Array<String>) {
	runApplication<AccountCalculatorApplication>(*args)
}
