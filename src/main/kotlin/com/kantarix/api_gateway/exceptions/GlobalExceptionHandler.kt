package com.kantarix.api_gateway.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(ex.message)
    }
}