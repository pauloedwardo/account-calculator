package com.example.account.calculator.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("accounts")
data class Account (
    @Id val id: String? = null,
    val name: String,
    val document: String,
    var balance: Long? = 0,
    val zipCode: String)