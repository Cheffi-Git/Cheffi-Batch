package com.message.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.Version

@Entity
class Review(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    private var viewCnt: Int,

    @Version
    private var version: Int? = null
) {

    fun increaseViewCount(count: Int) {
        this.viewCnt += count
    }


}
