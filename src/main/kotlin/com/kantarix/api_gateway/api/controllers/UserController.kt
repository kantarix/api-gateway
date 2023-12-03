package com.kantarix.api_gateway.api.controllers

import com.kantarix.api_gateway.api.client.UserClient
import com.kantarix.user.client.model.AuthRequestGen
import com.kantarix.user.client.model.PasswordRequestGen
import com.kantarix.user.client.model.RefreshTokenRequestGen
import com.kantarix.user.client.model.RegisterRequestGen
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController(
    private val userClient: UserClient,
) {

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequestGen) =
        userClient.register(registerRequest)

    @PostMapping("/auth")
    fun auth(@RequestBody authRequest: AuthRequestGen) =
        userClient.auth(authRequest)

    @PostMapping("/refresh")
    fun refresh(
        @RequestHeader("X-Access-Token") accessToken: String,
        @RequestBody refreshTokenRequest: RefreshTokenRequestGen,
    ) = userClient.refresh(accessToken, refreshTokenRequest)

    @PostMapping("/signout")
    fun signout(@RequestHeader("X-Access-Token") accessToken: String) =
        userClient.signout(accessToken)

    @DeleteMapping("/account")
    fun deleteAccount(
        @RequestHeader("X-Access-Token") accessToken: String,
        @RequestBody passwordRequest: PasswordRequestGen,
    ) = userClient.deleteAccount(accessToken, passwordRequest)

}