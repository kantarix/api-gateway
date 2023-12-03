package com.kantarix.api_gateway.config.filter

import com.auth0.jwt.exceptions.JWTVerificationException
import com.kantarix.api_gateway.api.services.RequestContext
import com.kantarix.api_gateway.util.JWTUtil
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationFilter(
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val jwtUtil: JWTUtil,
    private val routeValidator: RouteValidator,
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (routeValidator.isSecured(request)) {
            request.getHeader("X-Access-Token")
                ?.let {
                    try {
                        log.info { request.requestURI }
                        RequestContext.setUserId(jwtUtil.validateToken(it).getClaim("userId").asString().toInt())
                        filterChain.doFilter(request, response)
                    } catch (e: JWTVerificationException) {
                        throw RuntimeException("Unauthorized access.")
                    } catch (e: Exception) {
                        handlerExceptionResolver.resolveException(request, response, null, e)
                    } finally {
                        RequestContext.clear()
                    }
                }
                ?: throw RuntimeException("Missing authorization header.")
        }
    }
}