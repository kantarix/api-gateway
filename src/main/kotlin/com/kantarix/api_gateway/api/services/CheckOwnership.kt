package com.kantarix.api_gateway.api.services

import com.kantarix.api_gateway.api.client.DeviceClient
import com.kantarix.api_gateway.api.client.HomeClient
import com.kantarix.api_gateway.api.client.RoomClient
import org.springframework.stereotype.Component

@Component
class CheckOwnership(
    private val homeClient: HomeClient,
    private val roomClient: RoomClient,
    private val deviceClient: DeviceClient,
) {
    fun checkHomeOwnership(homeId: Int) =
        homeClient.checkOwnership1(
            homeId = homeId,
            ownerId = RequestContext.getUserId()
        ).takeIf { it }

    fun checkRoomOwnership(roomId: Int) =
        roomClient.checkOwnership(
            roomId = roomId,
            ownerId = RequestContext.getUserId()
        ).takeIf { it }

    fun checkDeviceOwnership(deviceId: Int) =
        deviceClient.checkOwnership(
            deviceId = deviceId,
            ownerId = RequestContext.getUserId()
        ).takeIf { it }
}