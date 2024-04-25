package com.message.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Review(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var viewCnt: Int,
) {

    fun increaseViewCountBy(count: Int) {
        this.viewCnt += count
    }

}
