package com.kantarix.api_gateway.api.controllers

import com.kantarix.api_gateway.api.client.RoomClient
import com.kantarix.api_gateway.api.services.CheckOwnership
import com.kantarix.home.client.model.RoomRequestGen
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rooms")
class RoomController(
    private val roomClient: RoomClient,
    private val checker: CheckOwnership,
) {

    @PostMapping
    fun createRoom(
        @RequestParam homeId: Int,
        @Validated @RequestBody room: RoomRequestGen,
    ) = checker.checkHomeOwnership(homeId)
        ?.let { roomClient.createRoom(homeId, room) }

    @PutMapping("/{roomId}")
    fun editRoom(
        @PathVariable("roomId") roomId: Int,
        @Validated @RequestBody room: RoomRequestGen,
    ) = checker.checkRoomOwnership(roomId)
        ?.let { roomClient.editRoom(roomId, room) }

    @DeleteMapping("/{roomId}")
    fun deleteRoom(
        @PathVariable("roomId") roomId: Int,
    ) = checker.checkRoomOwnership(roomId)
        ?.let { roomClient.deleteRoom(roomId) }

}