package com.kantarix.api_gateway.api.services

import org.springframework.stereotype.Component

@Component
class RequestContext {
    companion object {
        private val loggedInUserId = ThreadLocal<Int>()
        fun getUserId(): Int = loggedInUserId.get()
        fun setUserId(userId: Int) = loggedInUserId.set(userId)
        fun clear() = loggedInUserId.remove()
    }
}