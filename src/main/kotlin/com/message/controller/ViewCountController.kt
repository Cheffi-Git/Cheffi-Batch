package com.message.controller

import com.message.listener.ViewCountEvent
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

    /**
     * 1. 조회수 카운팅 요청을 받으면 ViewCountEvent를 발행
     * 2. ViewCountEvent를 성공적으로 발행하면 성공 메세지 응답
     * 3. 조회 기록 저장, 조회수 증가는 ViewCountListener가 백그라운드 스레드에서 비동기적으로 처리
     */
    @PostMapping("/reviews/count")
    fun viewCount(
        @Valid @RequestBody request: ViewCount.Request
    ): ViewCount.Response {
        eventPublisher.publishEvent(request.toEvent())
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
    ) {
        internal fun toEvent(): ViewCountEvent = ViewCountEvent(
            reviewId = reviewId!!,
            authenticated = authenticated!!,
            viewerId = viewerId
        )
    }

    data class Response(
        val message: String? = null
    )


}
