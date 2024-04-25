package com.message.service

import com.message.entity.Review
import com.message.repository.ReviewRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap

@Service
class ViewCountingBatchService(
    private val viewCountUpdateService: ViewCountUpdateService
) {

    /**
     *  ConcurrentHashMap 은 같은 버킷에 대해서만 락을 건다.
     */
    val countMap: ConcurrentHashMap<Long, Int> = ConcurrentHashMap()

    /**
     * 원자적으로 같은 리뷰 ID의 카운트를 증가 시킨다.
     */
    fun countView(reviewId: Long) {
        countMap.merge(reviewId, 1, Int::plus)
    }

    /**
     * 고정 주기(0.2초)로 countMap에 캐싱된 조회수를 DB에 업데이트한다.
     * 업데이트 과정도 마찬가지로 원자적으로 수행한다.
     */
    @Scheduled(fixedDelay = 200L)
    fun applyViewCount() {
        for ((key, value) in countMap) {
            viewCountUpdateService.updateViewCount(key, value)
            countMap.merge(key, value) { currentValue, oldValue ->
                currentValue - oldValue
            }
        }
    }
}


@Service
class ViewCountUpdateService(
    private val reviewRepository: ReviewRepository
) {

    /**
     * 배타락 조회 메서드
     */
    @Transactional(readOnly = true)
    fun getByIdForUpdate(id: Long): Review {
        return reviewRepository.findByIdForUpdate(id) ?: throw RuntimeException("ID에 해당하는 리뷰가 없습니다.")
    }

    @Transactional
    fun updateViewCount(id: Long, count: Int) {
        getByIdForUpdate(id).increaseViewCountBy(count);
    }

}


