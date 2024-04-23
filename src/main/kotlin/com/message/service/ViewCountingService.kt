package com.message.service

import com.message.controller.ViewCount
import com.message.entity.Review
import com.message.repository.ReviewRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap

@Service
class ViewCountingService(
//    private val viewCountingFailureRepository: ViewCountingFailureRepository,
    private val reviewRepository: ReviewRepository,
) {

    val countMap: CountMap = CountMap()

    //    @Transactional
    fun countView(request: ViewCount.Request) {
        countMap.addCountOn(id = request.reviewId!!)
    }

    @Transactional(readOnly = true)
    fun getByIdForUpdate(id: Long): Review {
        return reviewRepository.findByIdForUpdate(id) ?: throw RuntimeException("ID에 해당하는 리뷰가 없습니다.")
    }

    @Scheduled("")
    fun applyViewCount() = runBlocking {
        countMap.mutex.withLock {
            for(i in m)
        }
    }

}

class CountMap(
    val map: ConcurrentHashMap<Long, Count> = ConcurrentHashMap(),
    val mutex: Mutex = Mutex()
) {

    fun addCountOn(id: Long) {
        val count = map[id] ?: addNewCount(id)
        count.add()
    }

    fun addNewCount(id: Long): Count = runBlocking {
        mutex.withLock {
            if (map[id] == null)
                map[id] = Count(0)
        }
        return@runBlocking map[id]!!
    }


    class Count(
        var count: Int,
        val mutex: Mutex = Mutex()
    ) {
        fun add() = runBlocking {
            mutex.withLock {
                count++
            }
            if (count == 10_000)
                println("10000개 도달")
        }
    }
}
