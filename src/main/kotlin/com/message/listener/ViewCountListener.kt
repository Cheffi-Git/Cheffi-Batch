package com.message.listener

import com.message.service.ViewCountingBatchService
import com.message.service.ViewHistoryService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ViewCountListener(
    private val viewHistoryService: ViewHistoryService,
    private val viewCountingBatchService: ViewCountingBatchService,
) {

    /**
     * 유저의 조회 기록을 저장하는 로직(비동기)
     */
    @Async
    @EventListener(ViewCountEvent::class)
    fun onReadingSaveViewHistory(request: ViewCountEvent) {
        viewHistoryService.saveViewHistory(request);
    }

    /**
     * 조회수를 증가시키는 로직(비동기)
     */
    @Async
    @EventListener(ViewCountEvent::class)
    fun onReadingIncreaseViewCount(request: ViewCountEvent) {
        viewCountingBatchService.countView(request.reviewId)
    }

}

data class ViewCountEvent(
    val reviewId: Long,
    val authenticated: Boolean,
    val viewerId: Long? = null
)
