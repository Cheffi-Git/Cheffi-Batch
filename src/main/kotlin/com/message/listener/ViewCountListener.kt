package com.message.listener

import com.message.controller.ViewCount
import com.message.service.ViewCountingService
import com.message.service.ViewHistoryService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ViewCountListener(
    private val viewHistoryService: ViewHistoryService,
    private val viewCountingService: ViewCountingService
) {
    @Async
    @EventListener(ViewCount.Request::class)
    fun onReadingSaveViewHistory(request: ViewCount.Request) {
        viewHistoryService.saveViewHistory(request);
    }

    @Async
    @EventListener(ViewCount.Request::class)
    fun onReadingIncreaseViewCount(request: ViewCount.Request) {
        viewCountingService.countView(request)
    }
}
