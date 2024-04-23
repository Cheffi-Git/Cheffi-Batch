package com.message.service

import com.message.controller.ViewCount
import com.message.entity.ViewHistory
import com.message.repository.ViewHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ViewHistoryService(
    private val viewHistoryRepository: ViewHistoryRepository
) {
    @Transactional
    fun saveViewHistory(request: ViewCount.Request) {
        viewHistoryRepository.save(ViewHistory(reviewId = request.reviewId!!, viewerId = request.viewerId))
    }

}
