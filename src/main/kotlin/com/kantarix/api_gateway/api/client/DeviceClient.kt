package com.kantarix.api_gateway.api.client

import com.kantarix.device.client.api.DeviceApi
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DeviceClient : DeviceApi(
    basePath = "http://localhost:5003",
    restTemplate = RestTemplate()
)