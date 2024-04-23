package com.message.controller

import jakarta.validation.Valid
import org.jetbrains.annotations.NotNull
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class MessageController(
) {
    private val log = LoggerFactory.getLogger(MessageController::class.java)

    @PostMapping("/messages")
    fun sendMessage(
        @Valid @RequestBody request: SendMessage.Request
    ): String {
        Thread.sleep(Random.nextLong(5L) * 1000)
        log.info("{}", request)
        return "Success"
    }

}

class SendMessage {

    data class Request(
        @field:NotNull
        val message: String? = null,
        @field:NotNull
        val id: Int? = null
    )

    data class Response(
        val message: String? = null
    )
}

