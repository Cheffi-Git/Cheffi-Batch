package com.message.controller

import jakarta.validation.Valid
import org.jetbrains.annotations.NotNull
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class ViewCountController(
    private val eventPublisher: ApplicationEventPublisher
) {
//    private val log = LoggerFactory.getLogger(ViewCountController::class.java)

    @PostMapping("/reviews/count")
    fun viewCount(
        @Valid @RequestBody request: ViewCount.Request
    ): ViewCount.Response {
//        log.info("{}", request)

        eventPublisher.publishEvent(request)

        return ViewCount.Response("Success")
    }

}


class ViewCount {

    data class Request(
        @field:NotNull
        val reviewId: Long? = null,
        @field:NotNull
        val authenticated: Boolean? = null,
        val viewerId: Long? = null
    )

    data class Response(
        val message: String? = null
    )

}
