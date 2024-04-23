package com.message.repository;

import com.message.entity.ViewHistory
import org.springframework.data.jpa.repository.JpaRepository

interface ViewHistoryRepository : JpaRepository<ViewHistory, Long> {
}
