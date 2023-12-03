package com.kantarix.api_gateway.api.client

import com.kantarix.home.client.api.RoomApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class RoomClient : RoomApi(
    basePath = "http://localhost:5002",
    restTemplate = RestTemplate()
)