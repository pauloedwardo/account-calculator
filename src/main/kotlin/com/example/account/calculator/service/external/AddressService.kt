package com.example.account.calculator.service.external

import com.example.account.calculator.model.Address
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class AddressService(val addressClient: AddressClient) {

    fun get(zipCode: String): Address? {
        logger.info("Calling address api to zipcode $zipCode")

        var address: Address? = null

        try {
            address = addressClient.getAddress(zipCode)

            logger.info("Address retrieved: $address")
        } catch (ex: Exception) {
            logger.error("Failed to get address by zipcode.", ex)
        }

        return address
    }
}