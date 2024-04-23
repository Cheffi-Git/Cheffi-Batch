package com.message.repository;

import com.message.entity.ViewCountingFailure
import org.springframework.data.jpa.repository.JpaRepository

interface ViewCountingFailureRepository : JpaRepository<ViewCountingFailure, Long> {
}
