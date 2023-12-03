package com.kantarix.api_gateway.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JWTUtil(
    @Value("\${jwt.secret}")
    private val secret: String,
) {

    fun validateToken(token: String): DecodedJWT =
        JWT.require(Algorithm.HMAC256(secret))
            .withSubject("user details")
            .build()
            .verify(token)

}