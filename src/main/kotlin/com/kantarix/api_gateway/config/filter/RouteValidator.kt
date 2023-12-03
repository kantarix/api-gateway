package com.kantarix.api_gateway.config.filter

import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class RouteValidator {

    val isSecured = { request: HttpServletRequest ->
        openApiEndpoints.none { request.requestURI.contains(it) }
    }

    companion object {
        val openApiEndpoints = listOf(
            "/register",
            "/auth",
            "/refresh",
            "/signout",
        )
    }

}