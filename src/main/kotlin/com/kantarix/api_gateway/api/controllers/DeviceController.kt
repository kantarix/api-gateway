package com.kantarix.api_gateway.api.controllers

import com.kantarix.api_gateway.api.client.DeviceClient
import com.kantarix.api_gateway.api.services.CheckOwnership
import com.kantarix.api_gateway.api.services.RequestContext
import com.kantarix.device.client.model.CreateDeviceRequestGen
import com.kantarix.device.client.model.DeviceCapabilitiesRequestGen
import com.kantarix.device.client.model.DeviceSimpleGen
import com.kantarix.device.client.model.EditDeviceRequestGen
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    private val deviceClient: DeviceClient,
    private val checker: CheckOwnership,
) {

    @GetMapping
    fun getDevices(): List<DeviceSimpleGen> =
        deviceClient.getDevices(RequestContext.getUserId())

    @GetMapping("/{deviceId}")
    fun getDevice(
        @PathVariable("deviceId") deviceId: Int,
    ) = checker.checkDeviceOwnership(deviceId)
        ?.let { deviceClient.getDevice(deviceId) }

    @PostMapping
    fun createDevice(
        @Validated @RequestBody deviceRequest: CreateDeviceRequestGen,
    ) = (deviceRequest.roomId
        ?.let { checker.checkRoomOwnership(it) }
        ?: checker.checkHomeOwnership(deviceRequest.homeId))
            ?.let { deviceClient.createDevice(RequestContext.getUserId(), deviceRequest) }

    @PutMapping("/{deviceId}")
    fun editDevice(
        @PathVariable("deviceId") deviceId: Int,
        @Validated @RequestBody deviceRequest: EditDeviceRequestGen,
    ) = (deviceRequest.roomId
        ?.let { checker.checkRoomOwnership(it) }
        ?: checker.checkHomeOwnership(deviceRequest.homeId))
            ?.let { checker.checkDeviceOwnership(deviceId) }
            ?.let { deviceClient.editDevice(deviceId, deviceRequest) }

    @PostMapping("/{deviceId}/control")
    fun editDeviceCapabilities(
        @PathVariable("deviceId") deviceId: Int,
        @Validated @RequestBody deviceCapabilities: DeviceCapabilitiesRequestGen,
    ) = checker.checkDeviceOwnership(deviceId)
        ?.let { deviceClient.editDeviceCapabilities(deviceId, deviceCapabilities) }

    @DeleteMapping("/{deviceId}")
    fun deleteDevice(
        @PathVariable("deviceId") deviceId: Int,
    ) = checker.checkDeviceOwnership(deviceId)
        ?.let { deviceClient.deleteDevice(deviceId) }

}