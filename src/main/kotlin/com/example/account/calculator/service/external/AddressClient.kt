package com.example.account.calculator.service.external

import com.example.account.calculator.model.Address
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "address-client", url = "viacep.com.br")
interface AddressClient {

    @GetMapping(value = ["/ws/{zipCode}/json"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getAddress(@PathVariable zipCode: String): Address
}