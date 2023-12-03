package com.kantarix.api_gateway.api.client

import com.kantarix.user.client.api.UserApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class UserClient : UserApi(
    basePath = "http://localhost:5001",
    restTemplate = RestTemplate()
)