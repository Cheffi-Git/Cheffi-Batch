package com.message.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
class ViewHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    private var viewerId: Long? = null,

    @NotNull
    private val reviewId: Long
) {



}
