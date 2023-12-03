package com.kantarix.api_gateway.api.controllers

import com.kantarix.api_gateway.api.client.HomeClient
import com.kantarix.api_gateway.api.services.CheckOwnership
import com.kantarix.api_gateway.api.services.RequestContext
import com.kantarix.home.client.model.HomeRequestGen
import com.kantarix.home.client.model.HomeSimpleGen
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/homes")
class HomeController(
    private val homeClient: HomeClient,
    private val checker: CheckOwnership,
) {

    @GetMapping
    fun getHomes(): List<HomeSimpleGen> = 
        homeClient.getHomes(RequestContext.getUserId())

    @GetMapping("/{homeId}")
    fun getHome(
        @PathVariable("homeId") homeId: Int,
    ) = checker.checkHomeOwnership(homeId)
        ?.let { homeClient.getHome(homeId) }

    @PostMapping
    fun createHome(
        @Validated @RequestBody home: HomeRequestGen,
    ) = homeClient.createHome(RequestContext.getUserId(), home)

    @PutMapping("/{homeId}")
    fun editHome(
        @PathVariable("homeId") homeId: Int,
        @Validated @RequestBody home: HomeRequestGen,
    ) = checker.checkHomeOwnership(homeId)
        ?.let { homeClient.editHome(homeId, home) }

    @DeleteMapping("/{homeId}")
    fun deleteHome(
        @PathVariable("homeId") homeId: Int,
    ) = checker.checkHomeOwnership(homeId)
        ?.let { homeClient.deleteHome(homeId) }

}