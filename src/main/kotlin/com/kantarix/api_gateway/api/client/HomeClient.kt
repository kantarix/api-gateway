package com.kantarix.api_gateway.api.client

import com.kantarix.home.client.api.HomeApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class HomeClient : HomeApi(
    basePath = "http://localhost:5002",
    restTemplate = RestTemplate()
)